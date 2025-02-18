package com.example.newsfeed.friend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FriendRequest {

    @NotBlank
    private Long following;

}
