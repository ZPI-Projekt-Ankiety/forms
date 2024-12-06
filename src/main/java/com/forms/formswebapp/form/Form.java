package com.forms.formswebapp.form;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "forms")
@Builder
public class Form {

    @Id
    private String id;

    private String title;

    private LocalDateTime closingTime;

    private String link;

    private List<String> questions;

    @Builder.Default
    private Status status = Status.OPEN;


    enum Status {
        OPEN,
        CLOSED
    }

    void expire() {
        this.status = Status.CLOSED;
    }

}
