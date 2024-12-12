package com.forms.formswebapp.mail;

import com.sendgrid.helpers.mail.objects.Personalization;

import java.util.HashMap;
import java.util.Map;

class DynamicTemplate extends Personalization {
    private final Map<String, Object> dynamicTemplateData = new HashMap<>();

    void add(String key, String value) {
        dynamicTemplateData.put(key, value);
    }

    void addAll(Map<String, Object> data) {
        dynamicTemplateData.putAll(data);
    }

    @Override
    public Map<String, Object> getDynamicTemplateData() {
        return dynamicTemplateData;
    }
}