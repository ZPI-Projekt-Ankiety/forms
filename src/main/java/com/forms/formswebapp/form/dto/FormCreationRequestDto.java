package com.forms.formswebapp.form.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FormCreationRequestDto(
        String userEmail,
        String title,
        LocalDateTime closingTime,
        List<String> questions
) {

}
