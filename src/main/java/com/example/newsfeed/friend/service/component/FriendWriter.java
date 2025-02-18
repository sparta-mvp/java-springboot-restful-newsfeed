package com.example.newsfeed.friend.service.component;

import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.repository.FriendRepository;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
>>>>>>> 4776085 (Feat(friend): Create serivce & repository)
public class FriendWriter {

    private final FriendRepository friendRepository;

    private final UserFinder userFinder;



<<<<<<< HEAD
    public void saveFriend(Friend friend) {
        friendRepository.save(friend);
    }

    public void deleteFriend(Friend friend) {
        friendRepository.delete(friend);
    }

=======
    public void addFollow(Long toUserId, Long fromUserId) {
        Friend relation = getRelation(toUserId, fromUserId);
        friendRepository.save(relation);
    }

    public void deleteFollow(Long userId, Long deleteId) {
        Friend relation = getRelation(userId, deleteId);
        friendRepository.delete(relation);
    }


    private Friend getRelation(Long toUserId, Long fromUserId) {
        User toUser = userFinder.findActive(toUserId);
        User fromUser = userFinder.findActive(fromUserId);
        return new Friend(toUser, fromUser);
    }
>>>>>>> 4776085 (Feat(friend): Create serivce & repository)
}
