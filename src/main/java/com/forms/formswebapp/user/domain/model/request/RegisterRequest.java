package com.forms.formswebapp.user.domain.model.request;

import com.forms.formswebapp.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record RegisterRequest(
        @NotBlank(message = "Firstname is required")
        String firstname,
        @NotBlank(message = "Lastname is required")
        String lastname,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be between 6 characters")
        String password,
        LocalDate birthdate,
        User.Gender gender
) {

}