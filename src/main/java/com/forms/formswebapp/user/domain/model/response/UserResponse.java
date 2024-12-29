package com.forms.formswebapp.user.domain.model.response;

import com.forms.formswebapp.user.domain.User;

import java.util.List;

public record UserResponse(
        List<UserDto> users
) {

    public static UserResponse from(final List<User> users) {
        return new UserResponse(users.stream().map(UserDto::from).toList());
    }

}
