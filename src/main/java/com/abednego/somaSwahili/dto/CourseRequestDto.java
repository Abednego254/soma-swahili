package com.abednego.somaSwahili.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequestDto {

    @NotBlank(message = "Course title is required")
    private String title;

    private String description;

    @NotBlank(message = "Course level is required")
    private String level; // Beginner, Intermediate, Advanced

    @NotNull(message = "Tutor ID is required")
    private Long tutorId;
}
