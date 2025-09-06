package com.abednego.somaSwahili.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@PrimaryKeyJoinColumn(name = "id") // ensures it uses the same ID as from User table
public class Student extends User {

    @NotBlank(message = "Institution name is required")
    @Column(nullable = false)
    private String institution;

    @NotBlank(message = "Course name is required")
    @Column(nullable = false)
    private String course;

    @Column
    private int yearOfStudy;

    @Size(min = 5, message = "Registration number should be meaningful")
    @Column(unique = true)
    private String registrationNumber;
}
