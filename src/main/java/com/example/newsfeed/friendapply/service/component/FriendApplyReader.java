package com.example.newsfeed.friendapply.service.component;

import com.example.newsfeed.friendapply.entity.FriendApply;
import com.example.newsfeed.friendapply.repository.FriendApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendApplyReader {

    private final FriendApplyRepository applyRepository;


    public Page<FriendApply> findSentRequests(Long userId, Pageable pageable) {
        return applyRepository.findBySenderId(userId, pageable);
    }

    public Page<FriendApply> findReceivedRequests(Long userId, Pageable pageable) {
        return applyRepository.findByReceiverId(userId, pageable);
    }

    public boolean isExist(Long toUserId, Long fromUserId){
        return applyRepository.existsBySenderIdAndReceiverId(toUserId, fromUserId);
    }

    public FriendApply getApply(Long toUserId, Long fromUserId) {
        return applyRepository.findBySenderIdAndReceiverId(toUserId, fromUserId).get();
    }
}
