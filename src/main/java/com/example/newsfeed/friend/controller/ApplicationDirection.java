package com.example.newsfeed.friend.controller;

import com.example.newsfeed.friend.exception.NotValidDirectionException;
import java.util.Arrays;

public enum ApplicationDirection {

    // 뒤에 + ME 가 있다고 가정 -> from me, to me

    FROM,   // 내가 친구 신청
    TO;     // 나에게 친구 신청


    public static ApplicationDirection of(String direct) {
        return Arrays.stream(ApplicationDirection.values())
                .filter(apply -> apply.name().equals(direct))
                .findFirst()
                .orElseThrow(() -> new NotValidDirectionException());
    }
}
