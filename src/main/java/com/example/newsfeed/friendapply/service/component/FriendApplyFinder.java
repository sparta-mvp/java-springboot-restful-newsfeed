package com.example.newsfeed.friendapply.service.component;

import com.example.newsfeed.friendapply.entity.FriendApply;
import com.example.newsfeed.friendapply.exception.NotFoundApplyException;
import com.example.newsfeed.friendapply.repository.FriendApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendApplyFinder {

    private final FriendApplyRepository applyRepository;


    public FriendApply getSentRequestsToUser(Long sender, Long receiver){
        return applyRepository.findBySenderIdAndReceiverId(sender, receiver).orElseThrow( () -> new NotFoundApplyException());
    }

}
