package com.abednego.somaSwahili.dto.payment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long id;

    private Long studentId;
    private String studentName;

    private BigDecimal amount;
    private String type;
    private String status;

    private String referenceId;

    private Long walletTransactionId;
    private Long bookingId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
