package com.abednego.somaSwahili.controller;

import com.abednego.somaSwahili.dto.auth.LoginRequestDto;
import com.abednego.somaSwahili.dto.auth.LoginResponseDto;
import com.abednego.somaSwahili.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        log.info("Login request received for email: {}", loginRequest.getEmail());
        LoginResponseDto response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
