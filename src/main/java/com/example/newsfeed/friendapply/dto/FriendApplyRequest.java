package com.example.newsfeed.friendapply.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendApplyRequest {

    @NotNull(message = "친구 신청할 상대방의 id를 입력해주세요.")
    private Long userId;
}
