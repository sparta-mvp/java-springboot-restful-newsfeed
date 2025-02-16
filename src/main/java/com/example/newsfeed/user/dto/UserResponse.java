package com.example.newsfeed.user.dto;

import com.example.newsfeed.user.entity.InterestTag;
import com.example.newsfeed.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String name;
    private final String interestTag;

    private UserResponse(Long id, String name, InterestTag interestTag) {
        this.id = id;
        this.name = name;
        this.interestTag = interestTag.name();
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getInterestTag());
    }
}
