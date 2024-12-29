package com.forms.formswebapp.user.domain.model.response;

import com.forms.formswebapp.user.domain.User;
import com.forms.formswebapp.user.domain.model.shared.Role;


public record UserDto(
        String id,
        String email,
        Role role
) {

    public static UserDto from(final User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRole());
    }

}
