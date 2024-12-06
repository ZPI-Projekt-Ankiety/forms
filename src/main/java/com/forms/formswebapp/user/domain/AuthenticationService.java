package com.forms.formswebapp.user.domain;

import com.forms.formswebapp.user.domain.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.forms.formswebapp.user.domain.model.request.RegisterRequest;
import com.forms.formswebapp.user.domain.model.request.AuthenticationRequest;
import com.forms.formswebapp.user.domain.model.response.AuthenticationResponse;
import com.forms.formswebapp.user.security.JwtService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        final User user = userService.getUserByEmailOrThrow(request.email());
        final Map<String, Object> claims = userService.getUserClaims(user.getUsername());

        final String jwt = jwtService.generateToken(user.getUsername(), claims);
        return AuthenticationResponse.from(user, jwt);
    }

    public AuthenticationResponse register(final RegisterRequest request) {
        validate(request.email());

        final User user = User.from(request, passwordEncoder.encode(request.password()));
        final User savedUser = userService.saveUser(user);
        final Map<String, Object> claims = userService.getUserClaims(savedUser.getUsername());

        final String jwt = jwtService.generateToken(savedUser.getUsername(), claims);
        return AuthenticationResponse.from(savedUser, jwt);
    }


    private void validate(final String email) {
        if (userService.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already exists.");
        }
    }
}
