package com.forms.formswebapp.form.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FormDto(
        String title,
        LocalDateTime closingTime,
        String link,
        List<String> questions
) {

}
