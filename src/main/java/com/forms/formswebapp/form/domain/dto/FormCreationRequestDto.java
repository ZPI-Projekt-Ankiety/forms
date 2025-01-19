package com.forms.formswebapp.form.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FormCreationRequestDto(
        String title,
        LocalDateTime closingTime,
        Boolean isPersonalDataRequired,
        List<QuestionCreationDto> questions
) {

}
