package com.forms.formswebapp.form;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "forms")
@Builder
public class Form {

    @Id
    private String id;

    @Getter
    private String title;

    @Getter
    private LocalDateTime closingTime;

    private String link;

    @Getter
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
