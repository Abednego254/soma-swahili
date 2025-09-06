package com.abednego.somaSwahili.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;
    private String path;

    public ApiError(String message, HttpStatus status, String path) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
        this.path = path;
    }
}

