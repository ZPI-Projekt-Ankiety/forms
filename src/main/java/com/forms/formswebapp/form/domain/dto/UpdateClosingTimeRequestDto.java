package com.forms.formswebapp.form.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UpdateClosingTimeRequestDto (
        LocalDateTime newClosingTime
) {

}
