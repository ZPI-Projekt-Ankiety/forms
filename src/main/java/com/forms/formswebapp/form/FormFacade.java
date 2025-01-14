package com.forms.formswebapp.form;

import com.forms.formswebapp.form.domain.FormService;
import com.forms.formswebapp.form.domain.dto.FilledOutFormDto;
import com.forms.formswebapp.form.domain.dto.FormCreationRequestDto;
import com.forms.formswebapp.form.domain.dto.FormFillOutRequestDto;
import com.forms.formswebapp.form.domain.dto.FormLinkDto;
import com.forms.formswebapp.form.domain.dto.FormResponseDto;
import com.forms.formswebapp.form.domain.dto.UpdateClosingTimeRequestDto;
import com.forms.formswebapp.form.domain.dto.UserFormsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FormFacade {

    private final FormService formService;

    public FormLinkDto createForm(FormCreationRequestDto formCreationRequestDto, Authentication authentication) {
        return formService.createForm(formCreationRequestDto, authentication);
    }

    public FormResponseDto getFormResponseDtoByLink(String link) {
        return formService.getFormResponseDtoByLink(link);
    }

    public void fillOutForm(String linkId, FormFillOutRequestDto formFillOutRequestDto, Authentication authentication) {
        formService.fillOutForm(linkId, formFillOutRequestDto, authentication);
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

    public UpdateClosingTimeRequestDto updateFormClosingTime(String formId, LocalDateTime newClosingDate) {
        return formService.updateFormClosingTime(formId, newClosingDate);
    }

    public List<UserFormsDto> getUserForms(final String email) {
        return formService.getUserForms(email);
    }

    public List<FormResponseDto> getUserCreatedForms(String name) {
        return formService.getUserCreatedForms(name);
    }
}
