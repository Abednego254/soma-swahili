package com.abednego.somaSwahili.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentWalletRequestDto {

    @NotNull(message = "Student ID is required")
    private Long studentId;
}
