package com.forms.formswebapp.user;

import org.springframework.stereotype.Service;
import com.forms.formswebapp.user.domain.User;
import com.forms.formswebapp.user.domain.UserService;


@Service
public class UserServiceExternal {

    private final UserService userService;

    public UserServiceExternal(final UserService userService) {
        this.userService = userService;
    }


    public User getUserByEmailOrThrow(final String email) {
        return userService.getUserByEmailOrThrow(email);
    }
}
