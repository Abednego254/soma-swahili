package com.abednego.somaSwahili.dto.booking;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long tutorId;
    private String tutorName;
    private LocalDateTime scheduledDateTime;
    private int durationMinutes;
    private String status;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
