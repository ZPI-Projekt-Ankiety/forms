package com.forms.formswebapp.form.domain.dto;

import com.forms.formswebapp.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RespondentData {
    private String userEmail;
    private LocalDate birthdate;
    private User.Gender gender;


    public int age() {
        if (birthdate == null) {
            return 0;
        }
        return LocalDate.now().getYear() - birthdate.getYear();
    }
}
