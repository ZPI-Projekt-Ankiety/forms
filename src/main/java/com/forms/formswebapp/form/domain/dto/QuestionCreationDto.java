package com.forms.formswebapp.form.dto;

import java.util.List;

public record QuestionCreationDto(
        QuestionType questionType,
        boolean required,
        String questionText,
        List<String> possibleAnswers,
        List<Integer> correctAnswerIndexes

) {
}
