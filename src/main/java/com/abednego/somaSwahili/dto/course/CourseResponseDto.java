package com.abednego.somaSwahili.dto.course;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseDto {

    private Long id;
    private String title;
    private String description;
    private String level;

    private Long tutorId;
    private String tutorName; // convenience

    private List<Long> lessonIds;
    private List<Long> enrollmentIds;
}
