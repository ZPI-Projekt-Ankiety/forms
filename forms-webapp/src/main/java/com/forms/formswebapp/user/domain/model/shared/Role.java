package com.forms.formswebapp.user.domain.model.shared;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.forms.formswebapp.user.domain.model.shared.Privilege.DELETE_SURVEY;
import static com.forms.formswebapp.user.domain.model.shared.Privilege.MANAGE_USERS;
import static com.forms.formswebapp.user.domain.model.shared.Privilege.READ_SURVEY;
import static com.forms.formswebapp.user.domain.model.shared.Privilege.WRITE_NEW_SURVEY;


public enum Role {
    USER(Set.of(READ_SURVEY, WRITE_NEW_SURVEY)),
    ADMIN(Set.of(READ_SURVEY, WRITE_NEW_SURVEY, DELETE_SURVEY, MANAGE_USERS));

    private final Set<Privilege> privileges;

    Role(final Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        final List<SimpleGrantedAuthority> authorities = getPrivileges()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}