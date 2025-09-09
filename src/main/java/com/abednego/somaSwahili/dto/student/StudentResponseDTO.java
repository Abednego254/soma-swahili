package com.abednego.somaSwahili.dto.student;

import lombok.Data;

@Data
public class StudentResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String proficiencyLevel;       // e.g., BEGINNER, INTERMEDIATE, ADVANCED
    private String learningGoals;
    private String preferredLearningStyle;
    private String availability;
    private Double walletBalance;          // from StudentWallet
    private String createdAt;
    private String updatedAt;
}
