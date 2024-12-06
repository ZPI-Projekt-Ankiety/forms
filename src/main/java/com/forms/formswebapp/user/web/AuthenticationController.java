package com.forms.formswebapp.user.web;

import com.forms.formswebapp.user.domain.AuthenticationService;
import com.forms.formswebapp.user.domain.model.request.AuthenticationRequest;
import com.forms.formswebapp.user.domain.model.request.RegisterRequest;
import com.forms.formswebapp.user.domain.model.response.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody final RegisterRequest request) {
        final AuthenticationResponse authenticationResponse = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
    }

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody final AuthenticationRequest request) {
        final AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ResponseEntity.ok().body(authenticationResponse);
    }
    
}