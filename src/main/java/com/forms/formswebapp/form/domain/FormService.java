package com.forms.formswebapp.form.domain;

import com.forms.formswebapp.form.domain.dto.FilledOutFormDto;
import com.forms.formswebapp.form.domain.dto.FormAnswerDto;
import com.forms.formswebapp.form.domain.dto.FormCreationRequestDto;
import com.forms.formswebapp.form.domain.dto.FormFillOutRequestDto;
import com.forms.formswebapp.form.domain.dto.FormLinkDto;
import com.forms.formswebapp.form.domain.dto.FormQuestionDto;
import com.forms.formswebapp.form.domain.dto.FormResponseDto;
import com.forms.formswebapp.form.domain.dto.RespondentData;
import com.forms.formswebapp.form.domain.dto.UpdateClosingTimeRequestDto;
import com.forms.formswebapp.form.domain.dto.UserFormsDto;
import com.forms.formswebapp.form.domain.exception.FormNotFoundException;
import com.forms.formswebapp.mail.ExpiredFormNotificationDto;
import com.forms.formswebapp.mail.FilledOutFormNotificationDto;
import com.forms.formswebapp.mail.MailFacade;
import com.forms.formswebapp.user.UserFacade;
import com.forms.formswebapp.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormService {

    private final FormRepository formRepository;
    private final FormAnswerRepository formAnswerRepository;
    private final FormQuestionRepository formQuestionRepository;
    private final FilledOutFormRepository filledOutFormRepository;
    private final UserFacade userService;
    private final MailFacade mailFacade;

    public FormLinkDto createForm(FormCreationRequestDto formCreationRequestDto, Authentication authentication) {
        String uniqueLink = UUID.randomUUID().toString();
        User user = userService.getUserByEmailOrThrow(authentication.getName());

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

    public void fillOutForm(String linkId, FormFillOutRequestDto formFillOutRequestDto, Authentication authentication) {
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
                .respondentData(buildRespondentData(authentication, formFillOutRequestDto))
                .answers(savedFormAnswers)
                .build();
        filledOutFormRepository.save(filledOutForm);
        sendMailNotification(filledOutForm, form);
    }

    public void updateExpiredForms() {
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

    public FormResponseDto getFormResponseDtoByLink(String link) {
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
                .isPersonalDataRequired(form.getIsPersonalDataRequired())
                .build();
    }


    public List<UserFormsDto> getUserForms(final String email) {
        final User user = userService.getUserByEmailOrThrow(email);
        final List<Form> forms = formRepository.findByUser(user);

        return forms.stream().map(form -> {
            final FilledOutForm filledOutForm = filledOutFormRepository.findByFormId(form.getId()).orElse(null);
            return UserFormsDto.from(form, filledOutForm);
        }).toList();
    }

    public FilledOutForm getFilledOutForm(String filledOutFormId) {
        return filledOutFormRepository.findById(filledOutFormId).orElseThrow(
                () -> new EmptyResultDataAccessException("Filled out form not found", 1)
        );
    }

    public FilledOutFormDto getFilledOutFormDto(String filledOutFormId) {
        FilledOutForm filledOutForm = getFilledOutForm(filledOutFormId);

        return FilledOutFormDto.builder()
                .id(filledOutForm.getId())
                .respondentData(filledOutForm.getRespondentData())
                .formAnswers(filledOutForm.getAnswers().stream()
                        .map(formAnswer -> new FormAnswerDto(formAnswer.getQuestionId(), formAnswer.getAnswer())
                ).toList())
                .build();
    }

    public List<FilledOutFormDto> getAnswersForForm(String link) {
        Form formByLink = getFormByLink(link);
        List<FilledOutForm> answeredForms = filledOutFormRepository.findAllByFormIdIs(formByLink.getId());

        return answeredForms.stream().map(filledOutForm -> FilledOutFormDto.builder()
                .id(filledOutForm.getId())
                .filledOutAt(filledOutForm.getFilledOutTime())
                .formAnswers(filledOutForm.getAnswers().stream()
                        .map(formAnswer -> new FormAnswerDto(formAnswer.getQuestionId(), formAnswer.getAnswer())
                        ).toList())
                .respondentData(filledOutForm.getRespondentData())
                .build())
                .toList();
    }

    public long countAnswersByFormId(String formId) {
        Form form = getFormByLink(formId);
        List<FilledOutForm> answeredForms = filledOutFormRepository.findAllByFormIdIs(form.getId());

        return answeredForms.size();
    }

    public UpdateClosingTimeRequestDto updateFormClosingTime(String formId, LocalDateTime newClosingTime) {
        Form form = getFormByLink(formId);
        form.setClosingTime(newClosingTime);
        formRepository.save(form);

        return UpdateClosingTimeRequestDto.builder()
                .newClosingTime(form.getClosingTime())
                .build();
    }

    public List<FormResponseDto> getUserCreatedForms(String email) {
        User user = userService.getUserByEmailOrThrow(email);
        List<Form> userCreatedForms = formRepository.findByUser(user);
        return userCreatedForms.stream().map(form -> FormResponseDto.builder()
                .link(form.getLink())
                .title(form.getTitle())
                .closingTime(form.getClosingTime())
                .userEmail(form.getUser().getEmail())
                .isPersonalDataRequired(form.getIsPersonalDataRequired())
                .build()).toList();
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

    private RespondentData buildRespondentData(Authentication authentication, FormFillOutRequestDto request) {
        if (authentication == null) {
            return new RespondentData(request.userEmail(), request.birthDate(), request.gender());
        }
        User user = userService.getUserByEmailOrThrow(authentication.getName());
        return new RespondentData(user.getEmail(), user.getBirthdate(), user.getGender());
    }

    private String getFormQuestionNameById(final String questionId) {
        return formQuestionRepository.findById(questionId).map(FormQuestion::getQuestion).orElse(questionId);
    }

    private void sendMailNotification(final FilledOutForm filledOutForm, final Form form) {
        final RespondentData respondentData = filledOutForm.getRespondentData();

        if (respondentData == null) {
            log.info("sendMailNotification() - skipped sending mail notification, respondent data is required");
            return;
        }

        final FilledOutFormNotificationDto notification = new FilledOutFormNotificationDto(
                respondentData.getUserEmail(),
                form.getTitle(),
                filledOutForm.getAnswers().stream().map(f ->
                        new FilledOutFormNotificationDto.Answer(getFormQuestionNameById(f.getQuestionId()), f.getAnswer())
                ).toList());
        mailFacade.sendFilledOutFormNotification(notification);
    }

}
