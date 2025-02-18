package com.example.newsfeed.friend.dto;

import com.example.newsfeed.friend.entity.Friend;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FriendResponse {

    private Long fromUserId;
    private String fromUserName;


    public FriendResponse(Long fromUserId, String fromUserName) {
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
    }

    public static FriendResponse from(Friend friend) {
        return FriendResponse.builder()
                .fromUserId(friend.getFromUser().getId())
                .fromUserName(friend.getFromUser().getName())
                .build();
    }
}
