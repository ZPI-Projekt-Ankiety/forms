package com.forms.formswebapp.form.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FormResponseDto(
        String link,
        String title,
        LocalDateTime closingTime,
        String userEmail,
        List<FormQuestionDto> questions
) {

}
