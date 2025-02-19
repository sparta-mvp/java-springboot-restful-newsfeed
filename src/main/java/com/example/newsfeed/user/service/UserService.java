package com.example.newsfeed.user.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.ValidationException;
import com.example.newsfeed.user.dto.PasswordUpdateRequest;
import com.example.newsfeed.user.dto.SignupRequest;
import com.example.newsfeed.user.dto.UserResponse;
import com.example.newsfeed.user.dto.UserRequest;
import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.DuplicateUserException;
import com.example.newsfeed.user.exception.InvalidPasswordException;
import com.example.newsfeed.user.service.component.UserFinder;
import com.example.newsfeed.user.service.component.UserReader;
import com.example.newsfeed.user.service.component.UserWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.example.newsfeed.common.exception.ErrorCode.PASSWORD_CHECK_MISMATCH;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReader userReader;
    private final UserWriter userWriter;
    private final UserFinder userFinder;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void signup(SignupRequest requestDto) {

        validatePasswordMatch(requestDto.getPassword(), requestDto.getPasswordCheck());

        InterestTag interestTag = InterestTag.of(requestDto.getInterestTag());

        if (userReader.exists(requestDto.getEmail())){
            throw new DuplicateUserException();
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());


        userWriter.create(requestDto.getName(), encodedPassword, requestDto.getEmail(), interestTag);

    }

    @Transactional
    public void withdraw(LoginUser loginUser,  String password) {
        User user = userFinder.findActive(loginUser.getUserId());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.deActivate();

    }


    @Transactional
    public void updatePassword(LoginUser loginUser, PasswordUpdateRequest requestDto) {

        User user = userFinder.findActive(loginUser.getUserId());

        validateCorrectPassword(requestDto.getOldPassword(), user.getPassword());

        validatePasswordMatch(requestDto.getNewPassword(), requestDto.getNewPasswordCheck());

        validateNotSameAsOldPassword(requestDto.getNewPassword(), user.getPassword());

        String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());

        user.updatePassword(encodedPassword);

    }

    @Transactional
    public void updateUser(LoginUser loginUser, UserRequest requestDto) {
        User user = userFinder.findActive(loginUser.getUserId());

        if (!StringUtils.hasText(requestDto.getInterestTag()) && !StringUtils.hasText(requestDto.getName())) {
            throw new ValidationException(ErrorCode.NO_CHANGES_PROVIDED);
        }

        if (StringUtils.hasText(requestDto.getInterestTag())) {
            InterestTag interestTag = InterestTag.of(requestDto.getInterestTag());
            user.updateInterestTag(interestTag);
        }

        if (StringUtils.hasText(requestDto.getName())) {
            user.updateName(requestDto.getName());
        }


    }

    public UserResponse getUser(Long id) {
        User user = userFinder.findActive(id);

        return UserResponse.from(user);
    }


    private void validateCorrectPassword(String oldPassword, String password) {
        if (!passwordEncoder.matches(oldPassword, password)) {
            throw new InvalidPasswordException();
        }
    }

    private void validateNotSameAsOldPassword(String password, String encodedPassword) {
        if (passwordEncoder.matches(password,encodedPassword)) {
            throw new ValidationException(ErrorCode.SAME_PASSWORD);
        }
    }

    private void validatePasswordMatch(String password, String passwordCheck) {
        if (!StringUtils.hasText(password) || !password.equals(passwordCheck)) {
            throw new ValidationException(PASSWORD_CHECK_MISMATCH);
        }
    }

}
