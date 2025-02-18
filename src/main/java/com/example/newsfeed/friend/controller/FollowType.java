package com.example.newsfeed.friend.controller;

import com.example.newsfeed.friend.exception.NotValidGetFollowException;
import java.util.Arrays;

public enum FollowType {

    FOLLOWING,
    FOLLOWER;


    public static FollowType of(String type) {
        return Arrays.stream(FollowType.values())
                .filter(follow -> follow.name().equals(type))
                .findFirst()
                .orElseThrow(() -> new NotValidGetFollowException());
    }
}
