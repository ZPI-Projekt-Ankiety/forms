package com.forms.formswebapp.form.domain.dto;

import lombok.Builder;

@Builder
public record FormQuestionDto(
        String id,
        String question

) {
}
