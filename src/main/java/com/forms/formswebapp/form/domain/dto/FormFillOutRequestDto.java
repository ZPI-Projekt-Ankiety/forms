package com.forms.formswebapp.form.domain.dto;

import com.forms.formswebapp.user.domain.User;

import java.time.LocalDate;
import java.util.List;

public record FormFillOutRequestDto(
        String userEmail,
        LocalDate birthDate,
        User.Gender gender,
        List<FormAnswerDto> answers
) {
}
