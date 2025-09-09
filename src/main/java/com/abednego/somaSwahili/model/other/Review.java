package com.abednego.somaSwahili.model.other;

import com.abednego.somaSwahili.model.student.Student;
import com.abednego.somaSwahili.model.tutor.Tutor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating; // 1-5 stars

    private String comment;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
