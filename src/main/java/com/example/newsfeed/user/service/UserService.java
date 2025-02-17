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
        if (!isMatchingPassword(requestDto.getPassword(), requestDto.getPasswordCheck())) {
            throw new ValidationException(PASSWORD_CHECK_MISMATCH);
        }

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

        if (!isMatchingPassword(requestDto.getNewPassword(), requestDto.getNewPasswordCheck())) {
            throw new ValidationException(PASSWORD_CHECK_MISMATCH);
        }

        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }



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

    private boolean isMatchingPassword(String password, String passwordCheck) {
        return password != null && password.equals(passwordCheck);
    }

}
