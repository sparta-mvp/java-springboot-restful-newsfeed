package com.example.newsfeed.user.service.component;

import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.DeActivatedUserException;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFinder {

    private final UserRepository userRepository;

    public User findActive(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        if (user.isDeactivated()) {
            throw new DeActivatedUserException();
        }

        return user;
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
    }

    public InterestTag findByUserToTag(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        return user.getInterestTag();
    }

}
