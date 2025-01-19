package com.forms.formswebapp.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailFacade {

    private final MailService mailService;


    public void sendExpiredFormNotification(final ExpiredFormNotificationDto request) {
        try {
            mailService.sendExpiredFormNotification(request);
        } catch (final Exception e) {
            log.error("sendExpiredFormNotification() - error sending email - to = {}", request, e);
        }
    }


    public void sendFilledOutFormNotification(final FilledOutFormNotificationDto request) {
        try {
            mailService.sendFilledOutFormNotification(request);
        } catch (final Exception e) {
            log.error("sendFilledOutFormNotification() - error sending email - to = {}", request, e);
        }
    }

}
