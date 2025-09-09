package com.abednego.somaSwahili.dto.tutor;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorWalletResponseDto {

    private Long id;

    private Long tutorId;
    private String tutorName;

    private BigDecimal balance;
}
