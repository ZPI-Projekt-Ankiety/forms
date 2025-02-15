package com.forms.formswebapp.form.domain;

import com.forms.formswebapp.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "forms")
@Builder
@Setter
@Getter
public class Form {

    @Id
    private String id;

    private String title;

    @Builder.Default
    private LocalDateTime startDate = LocalDateTime.now();

    private LocalDateTime closingTime;

    private String link;

    private User user;

    private List<FormQuestion> questions;

    @Builder.Default
    private Status status = Status.OPEN;

    private Boolean isPersonalDataRequired;


    enum Status {
        OPEN,
        CLOSED
    }

    void expire() {
        this.status = Status.CLOSED;
    }

}
