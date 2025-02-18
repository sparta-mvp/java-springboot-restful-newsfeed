package com.example.newsfeed.friend.repository;

import com.example.newsfeed.friend.entity.Friend;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByToUserIdAndFromUserId(Long userId, Long friend);

    @Query("SELECT f from Friend f join fetch f.fromUser where f.fromUser.id = :fromUser")
    Page<Friend> findByFromUserId(Long fromUser, Pageable pageable);

    @Query("SELECT f from Friend f join fetch f.toUser where f.toUser.id = :toUser")
    Page<Friend> findByToUserId(Long toUser, Pageable pageable);

    Optional<Friend> findByToUserIdAndFromUserId(Long toUser, Long fromUser);
}
