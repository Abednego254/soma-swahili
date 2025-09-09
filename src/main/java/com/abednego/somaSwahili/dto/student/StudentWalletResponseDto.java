package com.abednego.somaSwahili.dto.student;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentWalletResponseDto {

    private Long id;

    private Long studentId;
    private String studentName;

    private BigDecimal balance;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
