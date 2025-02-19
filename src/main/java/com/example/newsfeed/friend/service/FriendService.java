package com.example.newsfeed.friend.service;

import com.example.newsfeed.friend.controller.ApplicationDirection;
import com.example.newsfeed.friend.dto.FriendResponse;
import com.example.newsfeed.friend.dto.TagUserResponse;
import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.exception.DuplicateRelationshipException;
import com.example.newsfeed.friend.exception.NotFriendException;
import com.example.newsfeed.friend.service.component.FriendFinder;
import com.example.newsfeed.friend.service.component.FriendReader;
import com.example.newsfeed.friend.service.component.FriendWriter;
import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import com.example.newsfeed.user.service.component.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserReader userReader;
    private final UserFinder userFinder;
    private final FriendFinder friendFinder;
    private final FriendWriter friendWriter;
    private final FriendReader friendReader;


    public Page<TagUserResponse> findByTag(String interestTag, Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        InterestTag tag = getTag(interestTag, userId);
        return userReader.findByTag(tag, pageable).map(user -> TagUserResponse.of(user.getId(), user.getName()));
    }


    @Transactional(readOnly = true)
    public Page<FriendResponse> getApplyList(ApplicationDirection direction, Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page< 신청Entity > applyList = getDirection(direction, userId, pageable);

        return applyList.map(신청Response::from);
    }


    public FriendResponse getFriend(Long userId, Long id) {
        Friend friend = friendFinder.getFriend(userId, id);
        return FriendResponse.from(friend);
    }


    public void addFollow(Long userId, Long following) {
        if (friendReader.isFollowing(userId, following)) {
            throw new DuplicateRelationshipException();
        }

        friendWriter.addFollow(userId, following);
    }


    public void deleteFollow(Long userId, Long deleteId) {
        if (!friendReader.isFollowing(userId, deleteId)) {
            throw new NotFriendException();
        }

        friendWriter.deleteFollow(userId, deleteId);
    }


    private InterestTag getTag(String interestTag, Long userId) {
        if (interestTag == null) {
            User active = userFinder.findActive(userId);
            return active.getInterestTag();
        }

        return InterestTag.of(interestTag);
    }

    private Page<Friend> getDirection(ApplicationDirection type, Long userId, Pageable pageable) {
        if (type.equals(ApplicationDirection.FROM)) {
            return 신청Service.findByToUser(userId, pageable);
        }
        return 신청Service.findByFromUser(userId, pageable);
    }
}
