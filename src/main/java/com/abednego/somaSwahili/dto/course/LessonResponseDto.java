package com.abednego.somaSwahili.dto.course;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonResponseDto {

    private Long id;
    private String title;
    private String content;

    private Long courseId;
    private String courseTitle;
}
