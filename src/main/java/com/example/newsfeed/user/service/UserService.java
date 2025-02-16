package com.example.newsfeed.user.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.ValidationException;
import com.example.newsfeed.user.dto.PasswordUpdateRequestDto;
import com.example.newsfeed.user.dto.SignupRequestDto;
import com.example.newsfeed.user.dto.UserResponseDto;
import com.example.newsfeed.user.dto.UserUpdateRequestDto;
import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.entity.UserStatus;
import com.example.newsfeed.user.exception.DeActivatedUserException;
import com.example.newsfeed.user.exception.DuplicateUserException;
import com.example.newsfeed.user.exception.InvalidPasswordException;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.example.newsfeed.common.exception.ErrorCode.PASSWORD_CHECK_MISMATCH;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void signup(SignupRequestDto requestDto) {
        if (!isMatchingPassword(requestDto.getPassword(), requestDto.getPasswordCheck())) {
            throw new ValidationException(PASSWORD_CHECK_MISMATCH);
        }

        InterestTag interestTag = InterestTag.of(requestDto.getInterestTag());

        if (userRepository.existsByEmail(requestDto.getEmail())){
            throw new DuplicateUserException();
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getName(),encodedPassword, requestDto.getEmail(),interestTag);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException());
    }

    @Transactional
    public void withdraw(LoginUser loginUser,  String password) {
        User user = userRepository.findById(loginUser.getUserId()).orElseThrow(() -> new UserNotFoundException());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.deActivate();

    }


    @Transactional
    public void updatePassword(LoginUser loginUser,PasswordUpdateRequestDto requestDto) {


        if (!isMatchingPassword(requestDto.getNewPassword(), requestDto.getNewPasswordCheck())) {
            throw new ValidationException(PASSWORD_CHECK_MISMATCH);
        }

        User user = userRepository.findById(loginUser.getUserId()).orElseThrow(() -> new UserNotFoundException());

        if (user.isDeactivated()) {
            throw new DeActivatedUserException();
        }

        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());

        user.updatePassword(encodedPassword);

    }




    @Transactional
    public void updateUser(LoginUser loginUser, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(loginUser.getUserId()).orElseThrow(() -> new UserNotFoundException());

        if (user.isDeactivated()) {
            throw new DeActivatedUserException();
        }

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

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        if (user.isDeactivated()) {
            throw new DeActivatedUserException();
        }

        return UserResponseDto.from(user);
    }

    private boolean isMatchingPassword(String password, String passwordCheck) {
        return password != null && password.equals(passwordCheck);
    }

}
