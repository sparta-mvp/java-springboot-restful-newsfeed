package com.example.newsfeed.friend.service;

import com.example.newsfeed.friend.dto.ApplicationStatus;
import com.example.newsfeed.friend.dto.FriendResponse;
import com.example.newsfeed.friend.dto.TagUserResponse;
import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.exception.DuplicateRelationshipException;
import com.example.newsfeed.friend.exception.NotFriendException;
import com.example.newsfeed.friend.service.component.FriendFinder;
import com.example.newsfeed.friend.service.component.FriendReader;
import com.example.newsfeed.friend.service.component.FriendWriter;
import com.example.newsfeed.friendapply.entity.FriendApply;
import com.example.newsfeed.friendapply.service.component.FriendApplyFinder;
import com.example.newsfeed.friendapply.service.component.FriendApplyWriter;
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

    private final FriendApplyFinder applyFinder;
    private final FriendApplyWriter applyWriter;


    public String addFriendOrRefuse(Long userId, Long applicant, ApplicationStatus status) {
        if (friendReader.isExist(userId, applicant)) {
            throw new DuplicateRelationshipException();
        }

        FriendApply sendUser = applyFinder.getSentRequestsToUser(applicant, userId);
        applyWriter.deleteApply(sendUser);

            if (status.getMessage().equals("Y")) {

                User user1 = userFinder.findActive(userId);
                User user2 = userFinder.findActive(applicant);
                Friend friend = new Friend(user1, user2);

                friendWriter.saveFriend(friend);
                return "친구 수락";
            }
        return "친구 거절";
    }


    @Transactional(readOnly = true)
    public Page<TagUserResponse> findByTag(String interestTag, Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        InterestTag tag = getTag(interestTag, userId);
        return userReader.findByTag(tag, pageable).map(user -> TagUserResponse.of(user.getId(), user.getName()));
    }


    public FriendResponse getFriend(Long userId, Long id) {
        Friend friend = friendFinder.getFriend(userId, id);
        return FriendResponse.from(friend);
    }


    @Transactional(readOnly = true)
    public Page<FriendResponse> findByFriends(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Friend> myFriends = friendReader.findMyFriends(userId, pageable);
        return myFriends.map(FriendResponse::from);
    }


    public void deleteFriend(Long userId, Long deleteId) {
        if (!friendReader.isExist(userId, deleteId)) {
            throw new NotFriendException();
        }

        Friend friend = friendFinder.getFriend(userId, deleteId);
        friendWriter.deleteFriend(friend);
    }


    private InterestTag getTag(String interestTag, Long userId) {
        if (interestTag == null) {
            User active = userFinder.findActive(userId);
            return active.getInterestTag();
        }

        return InterestTag.of(interestTag);
    }
}
