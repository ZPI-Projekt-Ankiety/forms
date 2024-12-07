package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class FormService {

    private final FormRepository formRepository;

    void createForm(FormRequestDto formRequestDto) {
        String uniqueLink = UUID.randomUUID().toString();

        Form form = Form.builder()
                .title(formRequestDto.title())
                .closingTime(formRequestDto.closingTime())
                .questions(formRequestDto.questions())
                .link(uniqueLink)
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

    FormRequestDto getFormByLink(String link) {
        Form form = formRepository.findByLink(link)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new FormRequestDto(
                form.getTitle(),
                form.getClosingTime(),
                form.getQuestions()
        );
    }
}
