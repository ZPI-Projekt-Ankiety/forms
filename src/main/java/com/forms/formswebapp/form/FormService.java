package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FormService {

    private final FormRepository formRepository;

    void createForm(FormRequestDto formRequestDto) {
        Form form = Form.builder()
                .title(formRequestDto.title())
                .closingTime(formRequestDto.closingTime())
                .questions(formRequestDto.questions())
                .build();
        formRepository.save(form);
    }
}
