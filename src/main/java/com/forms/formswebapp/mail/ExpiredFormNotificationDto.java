package com.forms.formswebapp.mail;

public record ExpiredFormNotificationDto(
        String recipient,
        String formName,
        String link
) {
}
