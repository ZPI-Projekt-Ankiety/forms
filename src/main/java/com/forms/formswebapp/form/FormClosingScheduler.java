package com.forms.formswebapp.form;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
class FormClosingScheduler {

    private final FormService formService;

    @Scheduled(cron = "0 0 * * * ?")
    void closeForms() {
        formService.updateExpiredForms();
    }
}
