package com.forms.formswebapp.user.domain.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forms.formswebapp.user.domain.model.shared.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import com.forms.formswebapp.user.domain.User;
import com.forms.formswebapp.user.domain.model.shared.TokenType;

import java.util.Collection;
import java.util.List;

public record AuthenticationResponse(
        String id,
        String email,
        Role role,
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("token_type")
        String tokenType
) {
    public static AuthenticationResponse from(final User user, final String token) {
        return new AuthenticationResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                token,
                TokenType.BEARER.name()
        );
    }
}