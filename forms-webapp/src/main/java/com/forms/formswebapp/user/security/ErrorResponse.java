package com.forms.formswebapp.user.security;


import java.time.Instant;

public record ErrorResponse (
    int status,
    String error,
    Instant timestamp,
    String message,
    String path) {


    public static ErrorResponse of(final int status, final String error, final String message, final String path) {
        return new ErrorResponse(status, error, Instant.now(), message, path);
    }
}