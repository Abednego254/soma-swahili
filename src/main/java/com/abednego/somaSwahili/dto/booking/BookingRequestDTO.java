package com.abednego.somaSwahili.dto.booking;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {
    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Tutor ID is required")
    private Long tutorId;

    @NotNull(message = "Scheduled date and time is required")
    private LocalDateTime scheduledDateTime;

    private int durationMinutes = 60;

    @NotNull(message = "Booking fee amount is required")
    private BigDecimal amount;
}
