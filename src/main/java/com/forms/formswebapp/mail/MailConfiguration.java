package com.forms.formswebapp.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.forms.mail")
@EnableConfigurationProperties(MailConfiguration.class)
class MailConfiguration {

    private SendGridConfig sendGrid;
    private TemplateConfig welcomeTemplate;
    private String webAppUrl;


    @Data
    static class SendGridConfig{
        private String apiKey;
        private String fromEmail;
        private String fromName;
    }

    @Data
    static class TemplateConfig {
        private String templateId;
        private List<String> requiredDynamicFields;
    }
}
