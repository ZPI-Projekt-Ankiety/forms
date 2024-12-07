package com.forms.formswebapp.form.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FormRequestDto(
        String title,
        LocalDateTime closingTime,
        List<String> questions
) {

}
