package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormService {

    private final FormRepository formRepository;

    public void createForm(FormDto formDto) {
        Form form = Form.builder()
                .title(formDto.title())
                .closingTime(formDto.closingTime())
                .questions(formDto.questions())
                .build();
        formRepository.save(form);
    }
}
