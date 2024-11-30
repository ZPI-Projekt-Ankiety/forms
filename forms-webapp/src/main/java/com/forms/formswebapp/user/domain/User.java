package com.forms.formswebapp.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.forms.formswebapp.user.domain.model.request.RegisterRequest;
import com.forms.formswebapp.user.domain.model.shared.Role;

import java.util.Collection;

@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;

    private Role role = Role.USER;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User() {
    }

    public User(String id, String email, String password, String firstname, String lastname, Role role) {
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

}
