package com.abednego.somaSwahili.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentRegistrationDto {

    @NotBlank(message = "First name is required")
    @Size(min = 2)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2)
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Institution is required")
    private String institution;

    @NotBlank(message = "Course is required")
    private String course;

    private Integer yearOfStudy;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

}
