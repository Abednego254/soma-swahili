package com.abednego.somaSwahili.model.tutor;

import com.abednego.somaSwahili.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@PrimaryKeyJoinColumn(name = "id") // inherit from User
public class Tutor extends User {

    @NotBlank(message = "Highest qualification is required")
    @Column(nullable = false)
    private String highestQualification;

    @NotBlank(message = "Teaching experience is required")
    @Column(nullable = false)
    private String teachingExperience;

    @Column(length = 500)
    private String bio;

    @Column(nullable = false)
    private String videoUrl;  // demo teaching video

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TutorStatus status = TutorStatus.PENDING;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TutorDocument> documents;

    @OneToOne(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private TutorWallet wallet;
}
