package com.forms.formswebapp.form.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UpdateClosingTimeRequestDto (
        LocalDateTime newClosingTime
) {

}
