package com.forms.formswebapp.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class MailService {
    private static final String EMAIL_ENDPOINT = "mail/send";

    private final Email fromEmail;
    private final SendGrid sendGrid;
    private final MailConfiguration mailConfiguration;


    void sendExpiredFormNotification(final ExpiredFormNotificationDto request) {
        //TODO - add link to the url
        final String webAppUrl = mailConfiguration.getWebAppUrl();
        final MailConfiguration.TemplateConfig welcomeTemplate = mailConfiguration.getWelcomeTemplate();
        final Map<String, Object> dynamicFields = Map.of("title", request.formName(), "dynamic_url", webAppUrl);

        validateDynamicFields(welcomeTemplate.getRequiredDynamicFields(), dynamicFields);
        final DynamicTemplate template = buildDynamicTemplate(dynamicFields, request.recipient());
        final Mail mail = buildMail(template, welcomeTemplate.getTemplateId());
        final Response response = sendMail(mail);
        log.info("sendExpiredFormNotification() - email sent - to = {} - response = {}", request.recipient(), response.getStatusCode());
    }

    private DynamicTemplate buildDynamicTemplate(final Map<String, Object> dynamicFields, final String recipient) {
        final Email email = new Email(recipient);
        final DynamicTemplate template = new DynamicTemplate();
        template.addAll(dynamicFields);
        template.addTo(email);
        return template;
    }

    private Mail buildMail(final DynamicTemplate template, final String templateId) {
        final Mail mail = new Mail();
        mail.setFrom(fromEmail);
        mail.setTemplateId(templateId);
        mail.addPersonalization(template);
        return mail;
    }

    private Response sendMail(final Mail mail) {
        try {
            final Request sendgridRequest = new Request();
            sendgridRequest.setMethod(Method.POST);
            sendgridRequest.setEndpoint(EMAIL_ENDPOINT);
            sendgridRequest.setBody(mail.build());
            return sendGrid.api(sendgridRequest);
        } catch (final IOException ex) {
            log.error("send() - error while sending email", ex);
            return new Response();
        }
    }

    private void validateDynamicFields(final List<String> requiredDynamicFields, final Map<String, Object> providedDynamicFields) {
        if (!requiredDynamicFields.stream().allMatch(providedDynamicFields::containsKey)) {
            throw new IllegalArgumentException("validateDynamicFields() - missing required dynamic fields");
        }
    }
}
