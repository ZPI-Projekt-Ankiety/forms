package com.forms.formswebapp.form.domain;

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
public class FormAnswer {

    @Id
    private String id;
    private String questionId;
    private String freetextAnswer;
    private List<Integer> chosenAnswerIndexes;

}
