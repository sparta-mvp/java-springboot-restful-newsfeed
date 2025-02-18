package com.example.newsfeed.user.service.component;

import com.example.newsfeed.friend.dto.FriendResponse;
import com.example.newsfeed.user.dto.UserResponse;
import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.DeActivatedUserException;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public InterestTag findByUserToTag(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        return user.getInterestTag();
    }
}
