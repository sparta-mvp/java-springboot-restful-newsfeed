package com.example.newsfeed.user.service;

import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.common.exception.ValidationException;
import com.example.newsfeed.user.dto.SignupRequestDto;
import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.DuplicateUserException;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private boolean isMatchingPassword(String password, String passwordCheck) {
        return password != null && password.equals(passwordCheck);
    }
}
