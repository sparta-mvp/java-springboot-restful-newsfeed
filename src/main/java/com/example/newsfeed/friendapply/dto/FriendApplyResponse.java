package com.example.newsfeed.friendapply.dto;

import com.example.newsfeed.friendapply.entity.FriendApply;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FriendApplyResponse {

    private Long toUserId;
    private String toUserName;

    public static FriendApplyResponse fromSender(FriendApply apply) {
        return FriendApplyResponse.builder()
                .toUserId(apply.getReceiver().getId())
                .toUserName(apply.getReceiver().getName())
                .build();
    }

    public static FriendApplyResponse fromReceiver(FriendApply apply) {
        return FriendApplyResponse.builder()
                .toUserId(apply.getSender().getId())
                .toUserName(apply.getSender().getName())
                .build();
    }
}
