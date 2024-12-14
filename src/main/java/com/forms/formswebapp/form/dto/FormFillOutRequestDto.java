package com.forms.formswebapp.form.dto;

import java.util.List;

public record FormFillOutRequestDto(
        String userEmail,
        List<FormAnswerDto> answers
) {
}
