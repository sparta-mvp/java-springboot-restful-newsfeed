package com.example.newsfeed.auth.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.auth.exception.LoginFailedException;
import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.DeActivatedUserException;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginUser login(String email, String password) {
        User user = getUserOrThrow(email);

        if (user.isDeactivated()) {
            throw new DeActivatedUserException();
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginFailedException();
        }

        return LoginUser.of(user.getId(), user.getName());

    }

    private User getUserOrThrow(String email) {
        try {
            return userService.getUserByEmail(email);
        } catch (UserNotFoundException e) {
            throw new LoginFailedException(e);
        }
    }
}
