package com.abednego.somaSwahili.dto.wallet;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransactionResponseDto {

    private Long id;
    private Long walletId;

    private String type;
    private BigDecimal amount;
    private BigDecimal balanceAfter;

    private String referenceId;
    private String metadata;

    private LocalDateTime createdAt;
}
