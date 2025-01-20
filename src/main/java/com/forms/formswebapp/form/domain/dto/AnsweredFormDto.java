package com.forms.formswebapp.form.domain.dto;

import java.time.LocalDateTime;

public record AnsweredFormDto(String link, String title, LocalDateTime answeredAt, String creatorEmail) {
}
