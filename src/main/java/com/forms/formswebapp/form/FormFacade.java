package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FormFacade {

    private final FormService formService;

    public FormLinkDto createForm(FormCreationRequestDto formCreationRequestDto) {
        return formService.createForm(formCreationRequestDto);
    }

    public FormResponseDto getFormResponseDtoByLink(String link) {
        return formService.getFormResponseDtoByLink(link);
    }

    public void fillOutForm(String linkId, FormFillOutRequestDto formFillOutRequestDto) {
        formService.fillOutForm(linkId, formFillOutRequestDto);
    }

    public FilledOutFormDto getFilledOutForm(String filledOutFormId) {
        return formService.getFilledOutFormDto(filledOutFormId);
    }

    public List<FilledOutFormDto> getAnswersForForm(String link) {
        return formService.getAnswersForForm(link);
    }

    public long getCountOfAnswersByFormId(String formId) {
        return formService.countAnswersByFormId(formId);
    }

    public Form updateFormClosingTime(String formId, LocalDateTime newClosingDate) {
        return formService.updateFormClosingTime(formId, newClosingDate);
    }

}
