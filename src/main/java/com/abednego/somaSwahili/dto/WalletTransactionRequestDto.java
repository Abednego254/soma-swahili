package com.abednego.somaSwahili.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransactionRequestDto {

    @NotNull(message = "Wallet ID is required")
    private Long walletId;

    @NotNull(message = "Transaction type is required")
    private String type; // DEPOSIT, LESSON_PAYMENT, SERVICE_FEE, etc.

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String referenceId;
    private String metadata;
}
