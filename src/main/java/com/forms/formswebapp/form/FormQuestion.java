package com.forms.formswebapp.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@Setter
public class FormQuestion {

    @Id
    private String id;
    private String question;

}
