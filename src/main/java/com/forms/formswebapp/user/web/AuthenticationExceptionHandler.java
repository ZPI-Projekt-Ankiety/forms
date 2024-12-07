package com.forms.formswebapp.user.web;

import com.forms.formswebapp.user.domain.exception.UserAlreadyExistsException;
import com.forms.formswebapp.common.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthenticationController.class)
class AuthenticationExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    ResponseEntity<?> handleException(final UserAlreadyExistsException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.CONFLICT, e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    ResponseEntity<?> handleException(final MethodArgumentNotValidException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
