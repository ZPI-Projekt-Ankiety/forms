package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.*;
import com.forms.formswebapp.form.exception.FormNotFoundException;
import com.forms.formswebapp.mail.ExpiredFormNotificationDto;
import com.forms.formswebapp.mail.MailFacade;
import com.forms.formswebapp.user.UserFacade;
import com.forms.formswebapp.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class FormService {

    private final FormRepository formRepository;
    private final FormAnswerRepository formAnswerRepository;
    private final FormQuestionRepository formQuestionRepository;
    private final FilledOutFormRepository filledOutFormRepository;
    private final UserFacade userService;
    private final MailFacade mailFacade;

    FormLinkDto createForm(FormCreationRequestDto formCreationRequestDto) {
        String uniqueLink = UUID.randomUUID().toString();
        User user = userService.getUserByEmailOrThrow(formCreationRequestDto.userEmail());

        List<FormQuestion> questions = formCreationRequestDto.questions().stream()
                .map(question -> FormQuestion.builder()
                        .question(question)
                        .build())
                .toList();

        List<FormQuestion> savedQuestions = formQuestionRepository.saveAll(questions);

        Form form = Form.builder()
                .title(formCreationRequestDto.title())
                .closingTime(formCreationRequestDto.closingTime())
                .link(uniqueLink)
                .user(user)
                .questions(savedQuestions)
                .build();

        Form savedForm = formRepository.save(form);
        return new FormLinkDto(savedForm.getLink());
    }

    void fillOutForm(String linkId, FormFillOutRequestDto formFillOutRequestDto) {
        Form form = getFormByLink(linkId);

        validateFormNotClosed(linkId, form);
        validateAllQuestionsAnsweredInRequest(formFillOutRequestDto, form);

        List<FormAnswer> answers = formFillOutRequestDto.answers().stream()
                .map(formAnswerDto -> FormAnswer.builder()
                        .questionId(formAnswerDto.questionId())
                        .answer(formAnswerDto.answer())
                        .build())
                .toList();
        List<FormAnswer> savedFormAnswers = formAnswerRepository.saveAll(answers);
        FilledOutForm filledOutForm = FilledOutForm.builder()
                .formId(form.getId())
                .userEmail(formFillOutRequestDto.userEmail())
                .answers(savedFormAnswers)
                .build();
        filledOutFormRepository.save(filledOutForm);
    }

    void updateExpiredForms() {
        final List<Form> expiredForms = formRepository.findByClosingTimeBeforeAndStatusNot(LocalDateTime.now(), Form.Status.CLOSED);
        expiredForms.forEach(this::updateExpired);
    }

    private void updateExpired(final Form form) {
        form.expire();
        formRepository.save(form);
        final ExpiredFormNotificationDto request = new ExpiredFormNotificationDto(form.getUser().getEmail(), form.getTitle(), form.getLink());
        mailFacade.sendExpiredFormNotification(request);
    }


    Form getFormByLink(String link) {
        return formRepository.findByLink(link)
                .orElseThrow(() -> new FormNotFoundException("Form not found"));
    }

    FormResponseDto getFormResponseDtoByLink(String link) {
        Form form = getFormByLink(link);

        return FormResponseDto.builder()
                .link(form.getLink())
                .title(form.getTitle())
                .closingTime(form.getClosingTime())
                .userEmail(form.getUser().getEmail())
                .questions(form.getQuestions().stream()
                        .map(formQuestion -> FormQuestionDto.builder()
                                .id(formQuestion.getId())
                                .question(formQuestion.getQuestion())
                                .build())
                        .toList())
                .build();
    }

    FilledOutForm getFilledOutForm(String filledOutFormId) {
        return filledOutFormRepository.findById(filledOutFormId).orElseThrow(
                () -> new EmptyResultDataAccessException("Filled out form not found", 1)
        );
    }

    FilledOutFormDto getFilledOutFormDto(String filledOutFormId) {
        FilledOutForm filledOutForm = getFilledOutForm(filledOutFormId);

        return FilledOutFormDto.builder()
                .id(filledOutForm.getId())
                .userEmail(filledOutForm.getUserEmail())
                .formAnswers(filledOutForm.getAnswers().stream()
                        .map(formAnswer -> new FormAnswerDto(formAnswer.getQuestionId(), formAnswer.getAnswer())
                ).toList())
                .build();
    }

    List<FilledOutFormDto> getAnswersForForm(String link) {
        Form formByLink = getFormByLink(link);
        List<FilledOutForm> answeredForms = filledOutFormRepository.findAllByFormIdIs(formByLink.getId());

        return answeredForms.stream().map(filledOutForm -> FilledOutFormDto.builder()
                .id(filledOutForm.getId())
                .formAnswers(filledOutForm.getAnswers().stream()
                        .map(formAnswer -> new FormAnswerDto(formAnswer.getQuestionId(), formAnswer.getAnswer())
                        ).toList())
                .userEmail(filledOutForm.getUserEmail())
                .build())
                .toList();
    }

    long countAnswersByFormId(String formId) {
        Form form = getFormByLink(formId);
        List<FilledOutForm> answeredForms = filledOutFormRepository.findAllByFormIdIs(form.getId());

        return answeredForms.size();
    }

    Form updateFormClosingTime(String formId, LocalDateTime newClosingTime) {
        Form form = getFormByLink(formId);
        form.setClosingTime(newClosingTime);
        return formRepository.save(form);
    }

    private static void validateFormNotClosed(String linkId, Form form) {
        if(form.getStatus() == Form.Status.CLOSED) {
            throw new IllegalArgumentException("Form with link %s is closed".formatted(linkId));
        }
    }

    private static void validateAllQuestionsAnsweredInRequest(FormFillOutRequestDto formFillOutRequestDto, Form form) {
        Set<String> questionIds = form.getQuestions().stream()
                .map(FormQuestion::getId)
                .collect(Collectors.toSet());
        Set<String> answeredQuestionIds = formFillOutRequestDto.answers().stream()
                .map(FormAnswerDto::questionId)
                .collect(Collectors.toSet());
        if(!questionIds.equals(answeredQuestionIds)) {
            throw new IllegalArgumentException("Question IDs in filled out form do not match with actual question IDs");
        }
    }
}
