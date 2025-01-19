package com.forms.formswebapp.form.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FormQuestionDto(
        String id,
        QuestionType questionType,
        boolean required,
        String questionText,
        List<String> possibleAnswers,
        List<Integer> correctAnswerIndexes


) {
}
