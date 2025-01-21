package com.forms.formswebapp.form.domain.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Builder
public record FilledOutFormDto(
        String id,
        String formId,
        RespondentData respondentData,
        LocalDateTime filledOutAt,
        List<FormAnswerDto> formAnswers
) {

    public LocalDate filledOutAtDay() {
        return filledOutAt == null ? LocalDate.now() : filledOutAt.toLocalDate();
    }
}
