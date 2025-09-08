package com.abednego.somaSwahili.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorWalletRequestDto {

    @NotNull(message = "Tutor ID is required")
    private Long tutorId;
}
