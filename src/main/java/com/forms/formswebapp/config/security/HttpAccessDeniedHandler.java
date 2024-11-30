package com.forms.formswebapp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forms.formswebapp.user.security.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class HttpAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    public HttpAccessDeniedHandler(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(final HttpServletRequest request,
                       final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        final ErrorResponse body = ErrorResponse.of(
                HttpServletResponse.SC_FORBIDDEN,
                "Forbidden",
                accessDeniedException.getMessage(),
                request.getServletPath()
                );
        mapper.writeValue(response.getOutputStream(), body);
    }
}