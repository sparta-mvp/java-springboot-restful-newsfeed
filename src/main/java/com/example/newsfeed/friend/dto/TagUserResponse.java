package com.example.newsfeed.friend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagUserResponse {

    private Long userId;
    private String userName;

    public static TagUserResponse of(Long userId, String userName) {
        return TagUserResponse.builder()
                .userId(userId)
                .userName(userName)
                .build();
    }
}
