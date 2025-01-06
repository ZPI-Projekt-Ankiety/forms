package com.forms.formswebapp.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailFacade {

    private final MailService mailService;


    public void sendExpiredFormNotification(final ExpiredFormNotificationDto request) {
        mailService.sendExpiredFormNotification(request);
    }

}
