package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FormFacade {

    private final FormService formService;

    public void createForm(FormDto formDto) {
        formService.createForm(formDto);
    }
}
