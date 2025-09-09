package com.abednego.somaSwahili.model.payment;

import com.abednego.somaSwahili.model.student.Student;
import com.abednego.somaSwahili.model.wallet.WalletTransaction;
import com.abednego.somaSwahili.model.booking.Booking;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payments_student_id", columnList = "student_id"),
        @Index(name = "idx_payments_reference", columnList = "referenceId", unique = true)
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Payer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type; // SERVICE_FEE or LESSON_PAYMENT

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // PENDING, COMPLETED, FAILED

    // Gateway reference (M-Pesa/Stripe/PayPal, etc.)
    @Column(nullable = false, unique = true, length = 100)
    private String referenceId;

    // Optional link to the wallet transaction created from this payment
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_transaction_id", unique = true)
    private WalletTransaction walletTransaction;

    // Optional link to a booking when type == LESSON_PAYMENT (you can also keep this null for SERVICE_FEE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
