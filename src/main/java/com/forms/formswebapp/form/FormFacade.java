package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FormFacade {

    private final FormService formService;

    public void createForm(FormRequestDto formRequestDto) {
        formService.createForm(formRequestDto);
    }

    public void getFormByLink(String link) { formService.getFormByLink(link); }
}
