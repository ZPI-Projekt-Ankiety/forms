package com.forms.formswebapp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forms.formswebapp.common.models.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
class HttpUnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final ErrorResponse body = ErrorResponse.of(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized",
                authException.getMessage(),
                request.getServletPath()
        );
        mapper.writeValue(response.getOutputStream(), body);
    }
}