package com.example.newsfeed.auth.dto;

import lombok.Getter;

@Getter
public class LoginUser {

    private final Long userId;
    private final String name;

    private LoginUser(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public static LoginUser of(Long userId, String name) {
        return new LoginUser(userId, name);
    }

}
