package com.forms.formswebapp.form.domain;

import com.forms.formswebapp.form.dto.QuestionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@Getter
@Setter
public class FormQuestion {

    @Id
    private String id;

    private QuestionType questionType;
    private boolean required;

    private String questionText;
    private List<String> possibleAnswers;
    private List<Integer> correctAnswerIndexes;

}
