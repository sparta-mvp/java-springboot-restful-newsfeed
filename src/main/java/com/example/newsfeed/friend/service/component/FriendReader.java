package com.example.newsfeed.friend.service.component;

import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendReader {

    private final FriendRepository friendRepository;

    public boolean isExist(Long toUser, Long fromUser) {
        return friendRepository.existsByToUserIdAndFromUserId(toUser, fromUser);
    }

    public Page<Friend> findMyFriends(Long userId, Pageable pageable) {
        return friendRepository.findByToUserId(userId, pageable);
    }

}
