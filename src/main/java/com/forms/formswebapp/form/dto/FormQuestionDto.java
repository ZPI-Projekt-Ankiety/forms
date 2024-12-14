package com.forms.formswebapp.form.dto;

import lombok.Builder;

@Builder
public record FormQuestionDto(
        String id,
        String question

) {
}
