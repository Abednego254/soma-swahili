package com.abednego.somaSwahili.repository;

import com.abednego.somaSwahili.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentId(Long studentId);
    Optional<Payment> findByReferenceId(String referenceId);
    Optional<Payment> findByBookingId(Long bookingId);
}
