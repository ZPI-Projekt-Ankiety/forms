package com.forms.formswebapp.form.domain.dto;

import java.util.List;

public record FormAnswerDto(
        String questionId,
        String freetextAnswer,
        List<Integer> chosenAnswerIndexes
) {
}
