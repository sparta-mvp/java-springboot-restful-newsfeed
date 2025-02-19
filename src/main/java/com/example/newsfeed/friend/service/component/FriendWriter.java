package com.example.newsfeed.friend.service.component;

import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.repository.FriendRepository;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FriendWriter {

    private final FriendRepository friendRepository;


    public void saveFriend(Friend friend) {
        friendRepository.save(friend);
    }

    public void deleteFriend(Friend friend) {
        friendRepository.delete(friend);
    }

}
