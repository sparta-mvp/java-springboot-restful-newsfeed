package com.example.newsfeed.user.service.component;

import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.entity.UserStatus;
import com.example.newsfeed.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReader {

    private final UserRepository userRepository;

    public boolean exists(String email) {
        return userRepository.existsByEmail(email);

    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Page<User> findByTag(InterestTag tag, Pageable pageable) {
        return userRepository.findByInterestTagAndStatus(tag, UserStatus.ACTIVE, pageable);
    }

}
