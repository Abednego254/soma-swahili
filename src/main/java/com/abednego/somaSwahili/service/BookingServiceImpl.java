package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.booking.BookingRequestDTO;
import com.abednego.somaSwahili.exception.InsufficientBalanceException;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.booking.Booking;
import com.abednego.somaSwahili.model.booking.BookingStatus;
import com.abednego.somaSwahili.model.student.Student;
import com.abednego.somaSwahili.model.student.StudentWallet;
import com.abednego.somaSwahili.model.tutor.Tutor;
import com.abednego.somaSwahili.model.tutor.TutorStatus;
import com.abednego.somaSwahili.model.tutor.TutorWallet;
import com.abednego.somaSwahili.model.wallet.WalletTransaction;
import com.abednego.somaSwahili.model.wallet.WalletTransactionType;
import com.abednego.somaSwahili.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;
    private final StudentWalletRepository studentWalletRepository;
    private final TutorWalletRepository tutorWalletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    @Transactional
    public Booking bookSession(BookingRequestDTO dto) {
        log.info("Processing booking request: studentId={}, tutorId={}, amount={}",
                dto.getStudentId(), dto.getTutorId(), dto.getAmount());

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + dto.getStudentId()));

        Tutor tutor = tutorRepository.findById(dto.getTutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with ID: " + dto.getTutorId()));

        // Ensure tutor is approved before they can be booked
        if (tutor.getStatus() != TutorStatus.APPROVED) {
            throw new IllegalStateException("Tutor is not approved to teach yet. Current status: " + tutor.getStatus());
        }

        StudentWallet wallet = studentWalletRepository.findByStudentId(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student wallet not found for student ID: " + dto.getStudentId()));

        // Check if student has sufficient wallet balance
        if (wallet.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient wallet balance. Available: " 
                    + wallet.getBalance() + ", Required: " + dto.getAmount());
        }

        // Deduct booking fee from student wallet to hold it
        wallet.setBalance(wallet.getBalance().subtract(dto.getAmount()));
        studentWalletRepository.save(wallet);

        // Record the debit transaction
        WalletTransaction transaction = WalletTransaction.builder()
                .wallet(wallet)
                .type(WalletTransactionType.LESSON_PAYMENT)
                .amount(dto.getAmount())
                .balanceAfter(wallet.getBalance())
                .referenceId(UUID.randomUUID().toString())
                .metadata("Hold for booking with tutor: " + tutor.getFirstName() + " " + tutor.getLastName())
                .build();
        walletTransactionRepository.save(transaction);

        // Create the booking
        Booking booking = Booking.builder()
                .student(student)
                .tutor(tutor)
                .scheduledDateTime(dto.getScheduledDateTime())
                .durationMinutes(dto.getDurationMinutes())
                .status(BookingStatus.PENDING)
                .build();

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getStudentBookings(Long studentId) {
        log.info("Fetching booking history for student ID: {}", studentId);
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }
        return bookingRepository.findByStudentId(studentId);
    }

    @Override
    public List<Booking> getTutorBookings(Long tutorId) {
        log.info("Fetching booking history for tutor ID: {}", tutorId);
        if (!tutorRepository.existsById(tutorId)) {
            throw new ResourceNotFoundException("Tutor not found with ID: " + tutorId);
        }
        return bookingRepository.findByTutorId(tutorId);
    }

    @Override
    @Transactional
    public Booking updateBookingStatus(Long bookingId, String statusStr) {
        log.info("Updating booking ID {} to status {}", bookingId, statusStr);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        BookingStatus newStatus = BookingStatus.valueOf(statusStr.toUpperCase());
        BookingStatus oldStatus = booking.getStatus();

        if (oldStatus == newStatus) {
            return booking;
        }

        // Handle transaction state machine transition
        if (newStatus == BookingStatus.DECLINED_BY_TUTOR || newStatus == BookingStatus.CANCELLED) {
            // Refund the locked amount back to student wallet
            BigDecimal refundAmount = BigDecimal.valueOf(100); // placeholder or retrieve dynamic value
            // Wait, we can log the transaction metadata or fetch actual booking payment, but for simplicity, let's see:
            // Since we don't store booking price directly inside Booking entity (it links via Payment, or we can assume a standard amount or use payment if present)
            // Let's check: Booking has Payment.
            // If payment is linked, we get payment amount. Otherwise, let's assume a default or get it from transaction metadata or define booking price.
            // Wait! Let's check: does Booking have a cost? No, but Payment does.
            // Let's find if there is a wallet transaction linked, or check if we can get the amount.
            // Wait, let's find the transaction by reference/metadata containing the booking info, or we can look up the Payment associated with this booking.
            BigDecimal amount = BigDecimal.ZERO;
            if (booking.getPayment() != null) {
                amount = booking.getPayment().getAmount();
            } else {
                // If no payment is linked, let's look for a transaction that was debited for this booking
                List<WalletTransaction> studentTxs = walletTransactionRepository.findByWalletStudentId(booking.getStudent().getId());
                for (WalletTransaction tx : studentTxs) {
                    if (tx.getType() == WalletTransactionType.LESSON_PAYMENT && tx.getMetadata() != null && tx.getMetadata().contains("Hold for booking")) {
                        amount = tx.getAmount();
                        break;
                    }
                }
            }

            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                StudentWallet studentWallet = studentWalletRepository.findByStudentId(booking.getStudent().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Student wallet not found"));
                studentWallet.setBalance(studentWallet.getBalance().add(amount));
                studentWalletRepository.save(studentWallet);

                WalletTransaction refundTx = WalletTransaction.builder()
                        .wallet(studentWallet)
                        .type(WalletTransactionType.CREDIT_BACK)
                        .amount(amount)
                        .balanceAfter(studentWallet.getBalance())
                        .referenceId(UUID.randomUUID().toString())
                        .metadata("Refund for declined/cancelled booking ID: " + bookingId)
                        .build();
                walletTransactionRepository.save(refundTx);
            }
        } else if (newStatus == BookingStatus.COMPLETED) {
            // Transfer funds to Tutor Wallet
            BigDecimal amount = BigDecimal.ZERO;
            if (booking.getPayment() != null) {
                amount = booking.getPayment().getAmount();
            } else {
                List<WalletTransaction> studentTxs = walletTransactionRepository.findByWalletStudentId(booking.getStudent().getId());
                for (WalletTransaction tx : studentTxs) {
                    if (tx.getType() == WalletTransactionType.LESSON_PAYMENT && tx.getMetadata() != null && tx.getMetadata().contains("Hold for booking")) {
                        amount = tx.getAmount();
                        break;
                    }
                }
            }

            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                TutorWallet tutorWallet = tutorWalletRepository.findByTutorId(booking.getTutor().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Tutor wallet not found"));
                // Pay tutor 90% (platform takes 10% fee)
                BigDecimal tutorPay = amount.multiply(BigDecimal.valueOf(0.90));
                tutorWallet.setBalance(tutorWallet.getBalance().add(tutorPay));
                tutorWalletRepository.save(tutorWallet);
            }
        }

        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }
}
