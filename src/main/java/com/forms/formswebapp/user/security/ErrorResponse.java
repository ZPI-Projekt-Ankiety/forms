package com.forms.formswebapp.user.security;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

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
}