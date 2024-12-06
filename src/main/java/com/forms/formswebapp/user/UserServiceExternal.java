package com.forms.formswebapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.forms.formswebapp.user.domain.User;
import com.forms.formswebapp.user.domain.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceExternal {

    private final UserService userService;

    public User getUserByEmailOrThrow(final String email) {
        return userService.getUserByEmailOrThrow(email);
    }
}
