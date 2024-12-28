package com.forms.formswebapp.form.dto;

import java.time.LocalDateTime;

public record UpdateClosingTimeRequestDto (
        LocalDateTime newClosingTime
) {

}
