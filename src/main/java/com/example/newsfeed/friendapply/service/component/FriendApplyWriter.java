package com.example.newsfeed.friendapply.service.component;

import com.example.newsfeed.friendapply.entity.FriendApply;
import com.example.newsfeed.friendapply.repository.FriendApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendApplyWriter {

    private final FriendApplyRepository applyRepository;


    public void deleteApply(FriendApply apply) {
        applyRepository.delete(apply);
    }

    public void saveApply(FriendApply apply) {
        applyRepository.save(apply);
    }
}
