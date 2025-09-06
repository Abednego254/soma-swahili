package com.abednego.somaSwahili.dto;

import com.abednego.somaSwahili.model.AdminLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminRegistrationDto {

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

    @NotBlank(message = "Admin code is required")
    @Size(min = 4, message = "Admin code should be at least 4 characters")
    private String adminCode;

    @NotBlank(message = "Department is required")
    private String department;

    private AdminLevel adminLevel;
}
