package com.forms.formswebapp.form.dto;

import java.util.List;

public record FormAnswerDto(
        String questionId,
        String freetextAnswer,
        List<Integer> chosenAnswerIndexes
) {
}
