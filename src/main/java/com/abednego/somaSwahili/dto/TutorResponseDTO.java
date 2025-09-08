package com.abednego.somaSwahili.dto;

import lombok.Data;
import java.util.List;

@Data
public class TutorResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String highestQualification;
    private String teachingExperience;
    private String bio;
    private String videoUrl;
    private String status;                     // TutorStatus name
    private List<TutorDocumentDTO> documents; // Uploaded documents
    private Double walletBalance;             // from TutorWallet
    private String createdAt;
    private String updatedAt;

    @Data
    public static class TutorDocumentDTO {
        private Long id;
        private String documentName;
        private String documentType;
    }
}
