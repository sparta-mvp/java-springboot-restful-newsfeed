package com.example.newsfeed.friendapply.repository;

import com.example.newsfeed.friendapply.entity.FriendApply;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendApplyRepository extends JpaRepository<FriendApply, Long> {

    @Query("SELECT f from FriendApply f join fetch f.receiver where f.receiver.id = :receiver")
    Page<FriendApply> findByReceiverId(Long receiver, Pageable pageable);

    @Query("SELECT f from FriendApply f join fetch f.sender where f.sender.id = :sender")
    Page<FriendApply> findBySenderId(Long sender, Pageable pageable);

    boolean existsBySenderIdAndReceiverId(Long sender, Long receiver);

    Optional<FriendApply> findBySenderIdAndReceiverId(Long sender, Long receiver);
}
