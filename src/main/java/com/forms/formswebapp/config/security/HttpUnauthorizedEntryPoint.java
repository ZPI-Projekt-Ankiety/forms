package com.forms.formswebapp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forms.formswebapp.common.models.ErrorResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
class HttpUnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        int statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) != null ?
                (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) : HttpServletResponse.SC_UNAUTHORIZED;
        String errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE) != null ?
                (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE) : authException.getMessage();

        ErrorResponse errorResponse = ErrorResponse.of(
                statusCode,
                HttpStatus.valueOf(statusCode).getReasonPhrase(),
                errorMessage,
                request.getServletPath()
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);
        mapper.writeValue(response.getOutputStream(), errorResponse);
        log.error("Error: " + authException.getMessage());
    }
}