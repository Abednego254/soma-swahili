package com.abednego.somaSwahili.dto;

import com.abednego.somaSwahili.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentUpdateDto {

    @Email(message = "Email should be valid")
    private String email;

    private String firstName;

    private String lastName;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String phone;

    private Role role;

    private String course;

    private  String RegistrationNumber;

    private String institution;

    private int yearOfStudy;
}
