package com.example.newsfeed.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendRequest {

    @NotNull(message = "응답할 사용자를 선택해주세요.")
    private Long user;

    @NotNull(message = "친구 신청에 대한 응답을 선택해주세요.")
    private ApplicationStatus status;

}
