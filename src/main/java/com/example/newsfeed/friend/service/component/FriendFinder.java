package com.example.newsfeed.friend.service.component;

import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.exception.FriendNotFoundException;
import com.example.newsfeed.friend.repository.FriendRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendFinder {

    private final FriendRepository friendRepository;


    public Friend getFriend(Long userId, Long friend) {
        Optional<Friend> getFriend = friendRepository.findByToUserIdAndFromUserId(userId, friend);
        if (!getFriend.isPresent()) {
            return friendRepository.findByFromUserIdAndToUserId(friend, userId).orElseThrow(() -> new FriendNotFoundException());
        }
        return getFriend.get();
    }
}
