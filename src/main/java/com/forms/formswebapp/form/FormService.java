package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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


    void updateExpiredForms() {
        final List<Form> expiredForms = formRepository.findByClosingTimeBeforeAndStatusNot(LocalDateTime.now(), Form.Status.CLOSED);
        if (expiredForms.isEmpty()) {
            return;
        }
        expiredForms.forEach(Form::expire);
        formRepository.saveAll(expiredForms);
    }
}
