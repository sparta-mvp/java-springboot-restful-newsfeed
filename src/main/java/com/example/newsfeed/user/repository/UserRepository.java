package com.example.newsfeed.user.repository;

import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.entity.UserStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Page<User> findByInterestTagAndStatus(InterestTag tag, UserStatus status, Pageable pageable);
}
