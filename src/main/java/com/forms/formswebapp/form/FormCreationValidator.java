package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormCreationRequestDto;
import com.forms.formswebapp.form.dto.QuestionCreationDto;
import com.forms.formswebapp.form.dto.QuestionType;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
class FormCreationValidator {

    public void validate(FormCreationRequestDto formCreationRequestDto) {

//        for (QuestionCreationDto question : formCreationRequestDto.questions()) {
//            if (question.questionType() == QuestionType.FREETEXT
//                    && (!CollectionUtils.isEmpty(question.chosenAnswerIndexes())
//                    || !CollectionUtils.isEmpty(question.possibleAnswers()))) {
//                throw new IllegalArgumentException("Wrong fields filled out for freetext form");
//            }
//
//            if (question.questionType() == QuestionType.SINGLE
//                    && (CollectionUtils.isEmpty(question.possibleAnswers()))
//                    || CollectionUtils.isEmpty(question.chosenAnswerIndexes())
//                    || question.chosenAnswerIndexes().size() != 1) {
//                throw new IllegalArgumentException("Wrong fields filled out for single-pick form");
//            }
//
//            if (question.questionType() == QuestionType.MULTIPLE
//                    && (CollectionUtils.isEmpty(question.possibleAnswers())
//                    || CollectionUtils.isEmpty(question.chosenAnswerIndexes())
//                    || question.chosenAnswerIndexes().size() <= question.possibleAnswers().size()
//                    || indexExceedsListSize(question)
//                    || containsDuplicates(question.chosenAnswerIndexes()))) {
//                throw new IllegalArgumentException("Wrong fields filled out for multi-pick form");
//            }
//
//        }
    }

    private boolean containsDuplicates(List<Integer> integers) {
        return new HashSet<>(integers).size() < integers.size();
    }

    private boolean indexExceedsListSize(QuestionCreationDto question) {
        return question.correctAnswerIndexes().stream()
                .anyMatch(integer -> integer > question.possibleAnswers().size());
    }
}
