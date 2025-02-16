package com.example.newsfeed.user.service.component;

import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
