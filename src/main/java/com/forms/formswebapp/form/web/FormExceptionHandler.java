package com.forms.formswebapp.form.web;

import com.forms.formswebapp.common.models.ErrorResponse;
import com.forms.formswebapp.form.FormController;
import com.forms.formswebapp.form.exception.FormNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = FormController.class)
class FormExceptionHandler {

    @ExceptionHandler({FormNotFoundException.class})
    ResponseEntity<?> handleException(final FormNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
