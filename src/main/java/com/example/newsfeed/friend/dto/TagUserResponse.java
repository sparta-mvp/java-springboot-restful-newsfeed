package com.example.newsfeed.friend.dto;

import com.example.newsfeed.user.entity.User;
import lombok.Builder;
import lombok.Getter;

//TODO: 이 부분 위치 [ user vs friend ] 어디로 이동할지?
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
