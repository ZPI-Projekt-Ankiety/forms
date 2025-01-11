package com.forms.formswebapp.form.dto;

import com.forms.formswebapp.form.domain.FilledOutForm;
import com.forms.formswebapp.form.domain.Form;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record UserFormsDto(
        String link,
        String title,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String userEmail,
        Integer questionsCount,
        Integer answersCount
) {

    public static UserFormsDto from(final Form form, @Nullable final FilledOutForm filledOutForm) {
        return new UserFormsDto(
                form.getLink(),
                form.getTitle(),
                form.getStartDate(),
                form.getClosingTime(),
                form.getUser().getEmail(),
                form.getQuestions() == null ? 0 : form.getQuestions().size(),
                filledOutForm == null ? 0 : filledOutForm.getAnswers().size()
        );
    }


}
