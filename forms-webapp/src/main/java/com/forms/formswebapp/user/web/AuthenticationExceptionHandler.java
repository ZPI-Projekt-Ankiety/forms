package com.forms.formswebapp.user.web;

import com.forms.formswebapp.user.domain.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthenticationController.class)
class AuthenticationExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    ResponseEntity<?> handleException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
