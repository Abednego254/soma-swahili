package com.abednego.somaSwahili.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonRequestDto {

    @NotBlank(message = "Lesson title is required")
    private String title;

    private String content; // text, video url, etc.

    @NotNull(message = "Course ID is required")
    private Long courseId;
}
