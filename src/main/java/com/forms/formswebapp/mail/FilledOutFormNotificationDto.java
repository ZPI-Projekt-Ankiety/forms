package com.forms.formswebapp.mail;

import java.util.List;

public record FilledOutFormNotificationDto(
        String recipient,
        String formName,
        List<Answer> answers
) {

    public record Answer(String question, String answer) {
    }

}
