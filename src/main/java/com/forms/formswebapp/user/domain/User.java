package com.forms.formswebapp.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.forms.formswebapp.user.domain.model.request.RegisterRequest;
import com.forms.formswebapp.user.domain.model.shared.Role;

import java.time.LocalDate;
import java.util.Collection;

@Document(collection = "users")
@Data
public class User implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String firstname;

    private String lastname;

    private Gender gender;

    private LocalDate birthdate;

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    User(final String id, final String email, final String password, final String firstname, final String lastname, final Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public static User from(final RegisterRequest request, final String encodedPassword) {
        return new User(null,
                request.email(),
                encodedPassword,
                request.firstname(),
                request.lastname(),
                Role.USER);
    }

    public enum Gender {
        MALE,
        FEMALE
    }


    public int age() {
        if (birthdate == null) {
            return 0;
        }
        return LocalDate.now().getYear() - birthdate.getYear();
    }


    public Gender getGender() {
        if (gender == null) {
            return Gender.MALE;
        }
        return gender;
    }

}
