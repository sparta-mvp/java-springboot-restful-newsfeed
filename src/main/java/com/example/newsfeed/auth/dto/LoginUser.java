package com.example.newsfeed.auth.dto;

import lombok.Getter;

@Getter
public class LoginUser {

    private final String email;
    private final String name;

    private LoginUser(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static LoginUser of(String email, String name) {
        return new LoginUser(email, name);
    }

}
