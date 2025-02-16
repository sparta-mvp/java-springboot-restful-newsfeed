package com.example.newsfeed.user.dto;

import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String interestTag;

    private UserResponseDto(Long id, String name, InterestTag interestTag) {
        this.id = id;
        this.name = name;
        this.interestTag = interestTag.name();
    }

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getInterestTag());
    }
}
