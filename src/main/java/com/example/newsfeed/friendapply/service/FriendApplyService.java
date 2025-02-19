package com.example.newsfeed.friendapply.service;

import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.service.component.FriendReader;
import com.example.newsfeed.friend.service.component.FriendWriter;
import com.example.newsfeed.friendapply.controller.ApplicationDirection;
import com.example.newsfeed.friendapply.dto.FriendApplyResponse;
import com.example.newsfeed.friendapply.entity.FriendApply;
import com.example.newsfeed.friendapply.exception.DuplicateApplyException;
import com.example.newsfeed.friendapply.exception.FriendAlreadyExistsException;
import com.example.newsfeed.friendapply.exception.SelfApplyException;
import com.example.newsfeed.friendapply.service.component.FriendApplyFinder;
import com.example.newsfeed.friendapply.service.component.FriendApplyReader;
import com.example.newsfeed.friendapply.service.component.FriendApplyWriter;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendApplyService {

    private final FriendApplyFinder applyFinder;
    private final FriendApplyReader applyReader;
    private final FriendApplyWriter applyWriter;

    private final UserFinder userFinder;
    private final FriendReader friendReader;
    private final FriendWriter friendWriter;


    public String apply(Long sender, Long receiver) {
        if (sender.equals(receiver)) {
            throw new SelfApplyException();
        }
        if (friendReader.isExist(sender, receiver)) {
            throw new FriendAlreadyExistsException();
        }
        if (applyReader.isExist(sender, receiver)) {
            throw new DuplicateApplyException();
        }


        User toUser = userFinder.findActive(sender);
        User fromUser = userFinder.findActive(receiver);
        Friend friend = new Friend(toUser, fromUser);

        if (applyReader.isExist(receiver, sender)) {
            FriendApply apply = applyReader.getApply(receiver, sender);
            applyWriter.deleteApply(apply);

            friendWriter.saveFriend(friend);
            return "이미 존재하는 요청입니다. 친구를 수락합니다.";
        }

        applyWriter.saveApply(new FriendApply(toUser, fromUser));

        return "친구 요청에 성공했습니다.";
    }

    @Transactional(readOnly = true)
    public Page<FriendApplyResponse> getApplyList(ApplicationDirection direction, Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return getDirection(direction, userId, pageable);
    }


    public void cancelApplication(Long toUserId, Long fromUserId) {
        FriendApply apply = applyFinder.getSentRequestsToUser(toUserId, fromUserId);
        applyWriter.deleteApply(apply);
    }


    private Page<FriendApplyResponse> getDirection(ApplicationDirection type, Long userId, Pageable pageable) {
        if (type.equals(ApplicationDirection.SEND)) {
            return applyReader.findSentRequests(userId, pageable).map(FriendApplyResponse::fromSender);
        }
        return applyReader.findReceivedRequests(userId, pageable).map(FriendApplyResponse::fromReceiver);
    }
}
