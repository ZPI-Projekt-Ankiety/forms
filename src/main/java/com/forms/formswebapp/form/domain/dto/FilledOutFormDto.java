package com.forms.formswebapp.form.domain.dto;

import lombok.Builder;

import java.util.List;


@Builder
public record FilledOutFormDto(
        String id,
        String userEmail,
        List<FormAnswerDto> formAnswers
) {
}
