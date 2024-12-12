package com.forms.formswebapp.mail;

import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SendGridConfiguration {

    @Value("${com.forms.mail.sendGrid.fromEmail}")
    private String fromEmail;

    @Value("${com.forms.mail.sendGrid.fromName}")
    private String fromName;

    @Value("${com.forms.mail.sendGrid.apiKey}")
    private String apiKey;


    @Bean
    SendGrid sendGrid() {
        return new SendGrid(apiKey);
    }

    @Bean
    Email fromEmail() {
        return new Email(fromEmail, fromName);
    }

}
