package com.forms.formswebapp.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse (
    int status,
    String error,
    Instant timestamp,
    String message,
    String path) {


    public static ErrorResponse of(final int status, final String error, final String message, final String path) {
        return new ErrorResponse(status, error, Instant.now(), message, path);
    }

    public static ErrorResponse of(final HttpStatus status, final RuntimeException e) {
        return new ErrorResponse(status.value(), status.name(), Instant.now(), e.getMessage(), null);
    }

    public static ErrorResponse of(final HttpStatus status, final MethodArgumentNotValidException e) {
        FieldError firstError = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .orElse(null);
        String errorMessage = firstError != null ? firstError.getDefaultMessage() : "Validation error";
        return new ErrorResponse(status.value(), status.getReasonPhrase(), Instant.now(), errorMessage, null);
    }
}