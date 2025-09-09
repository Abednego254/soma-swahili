package com.abednego.somaSwahili.model.student;

import com.abednego.somaSwahili.model.User;
import com.abednego.somaSwahili.model.other.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProficiencyLevel proficiencyLevel; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(length = 500)
    private String learningGoals;             // e.g., “Business Kiswahili”, “Travel”

    @Column(length = 100)
    private String preferredLearningStyle;    // e.g., “1-on-1”, “Group”, “Self-paced”

    @Column(length = 100)
    private String availability;              // e.g., “Evenings”, “Weekends”, “Flexible”

    // Optional convenience link to wallet (inverse side)
    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY)
    private StudentWallet wallet;
}
