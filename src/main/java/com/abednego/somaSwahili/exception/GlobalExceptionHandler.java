package com.abednego.somaSwahili.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
        EmailAlreadyExistsException.class,
        InvalidCredentialsException.class,
        UnauthorizedAccessException.class,
        ResourceNotFoundException.class,
        TokenExpiredException.class
    })
    public ResponseEntity<ApiError> handleCustomExceptions(
            RuntimeException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex instanceof EmailAlreadyExistsException) status = HttpStatus.CONFLICT;
        else if (ex instanceof InvalidCredentialsException) status = HttpStatus.UNAUTHORIZED;
        else if (ex instanceof UnauthorizedAccessException) status = HttpStatus.FORBIDDEN;
        else if (ex instanceof ResourceNotFoundException) status = HttpStatus.NOT_FOUND;
        else if (ex instanceof TokenExpiredException) status = HttpStatus.UNAUTHORIZED;

        logger.error("Handled custom exception: {} at [{}] with status {}", ex.getClass().getSimpleName(), request.getRequestURI(), status);

        ApiError error = new ApiError(
                ex.getMessage(),
                status,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        logger.warn("Validation failed at [{}]: {}", request.getRequestURI(), message);

        ApiError error = new ApiError(
                message,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }
}
