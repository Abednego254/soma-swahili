package com.abednego.somaSwahili.dto.auth;

import lombok.Data;

@Data
public class RegisterResponseDto {
    private Long userId;
    private String email;
    private String role;
    private String message; // e.g. "User registered successfully"
}
