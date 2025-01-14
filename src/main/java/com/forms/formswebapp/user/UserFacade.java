package com.forms.formswebapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.forms.formswebapp.user.domain.User;
import com.forms.formswebapp.user.domain.UserService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public User getUserByEmailOrThrow(final String email) {
        return userService.getUserByEmailOrThrow(email);
    }

    public Optional<User> getUserByEmail(final String email) {
        return userService.getUserByEmail(email);
    }
}
