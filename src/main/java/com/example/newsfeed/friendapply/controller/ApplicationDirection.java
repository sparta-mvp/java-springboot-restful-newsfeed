package com.example.newsfeed.friendapply.controller;

import com.example.newsfeed.friendapply.exception.NotValidDirectionException;
import java.util.Arrays;

public enum ApplicationDirection {

    SEND,   // 내가 친구 신청
    RECEIVE;     // 나에게 친구 신청


    public static ApplicationDirection of(String direct) {
        return Arrays.stream(ApplicationDirection.values())
                .filter(apply -> apply.name().equals(direct))
                .findFirst()
                .orElseThrow(() -> new NotValidDirectionException());
    }

}
