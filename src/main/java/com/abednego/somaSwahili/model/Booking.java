package com.abednego.somaSwahili.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "bookings", indexes = {
        @Index(name = "idx_bookings_student_id", columnList = "student_id"),
        @Index(name = "idx_bookings_tutor_id", columnList = "tutor_id"),
        @Index(name = "idx_bookings_datetime", columnList = "scheduledDateTime")
})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who booked
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Who will teach
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @Column(nullable = false)
    private LocalDateTime scheduledDateTime;

    @Column(nullable = false)
    private int durationMinutes; // e.g., 30, 60

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // PENDING, CONFIRMED, COMPLETED, CANCELLED, DECLINED_BY_TUTOR

    // Optional: direct link to a payment if you pay per booking
    @OneToOne(mappedBy = "booking", fetch = FetchType.LAZY)
    private Payment payment;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
