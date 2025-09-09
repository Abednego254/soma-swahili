package com.abednego.somaSwahili.dto.auth;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String role;  // ADMIN, TUTOR, STUDENT
    private String fullName;
    private String email;
}
