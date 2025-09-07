package com.abednego.somaSwahili.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "wallet_transactions", indexes = {
        @Index(name = "idx_wallet_tx_wallet_id", columnList = "wallet_id"),
        @Index(name = "idx_wallet_tx_reference", columnList = "referenceId", unique = true)
})
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which wallet this transaction belongs to
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private StudentWallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletTransactionType type; // DEPOSIT, LESSON_PAYMENT, SERVICE_FEE, CREDIT_BACK, BONUS, ADJUSTMENT_CREDIT, ADJUSTMENT_DEBIT

    // Always store positive numbers; direction implied by 'type'
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    // Balance AFTER applying this transaction (for audit)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal balanceAfter;

    // External or internal reference (e.g., M-Pesa/Stripe ref, booking id, payment id)
    @Column(length = 100, unique = true)
    private String referenceId;

    // Optional extra context (JSON or plain text)
    @Column(length = 1000)
    private String metadata;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
