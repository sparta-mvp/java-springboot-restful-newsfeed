package com.example.newsfeed.user.service.component;

import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserWriter {

    private final UserRepository userRepository;

    public void create(String name, String encodedPassword, String email, InterestTag interestTag) {
        User user = new User(name,encodedPassword, email,interestTag);
        userRepository.save(user);
    }
}
