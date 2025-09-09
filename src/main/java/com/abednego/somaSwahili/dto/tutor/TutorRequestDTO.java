package com.abednego.somaSwahili.dto.tutor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class TutorRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Highest qualification is required")
    private String highestQualification;

    @NotBlank(message = "Teaching experience is required")
    private String teachingExperience;

    private String bio;              // optional

    @NotBlank(message = "Video URL is required")
    private String videoUrl;

    private String status;           // map to TutorStatus enum in service layer

    private List<Long> documentIds;  // IDs of uploaded TutorDocuments
}
