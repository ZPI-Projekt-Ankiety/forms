package com.forms.formswebapp.form;

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
public class FilledOutForm {

    @Id
    private String id;
    private String formId;
    private String userEmail;
    private List<FormAnswer> answers;

}
