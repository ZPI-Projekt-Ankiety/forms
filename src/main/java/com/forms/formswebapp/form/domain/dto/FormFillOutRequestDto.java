package com.forms.formswebapp.form.domain.dto;

import java.util.List;

public record FormFillOutRequestDto(
        String userEmail,
        List<FormAnswerDto> answers
) {
}
