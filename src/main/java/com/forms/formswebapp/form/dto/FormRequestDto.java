package com.forms.formswebapp.form.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record FormRequestDto(
        String title,
        @NotNull
        LocalDateTime closingTime,
        List<String> questions
) {

}
