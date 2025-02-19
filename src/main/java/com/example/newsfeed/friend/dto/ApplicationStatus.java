package com.example.newsfeed.friend.dto;

import com.example.newsfeed.friend.exception.AcceptInvalidFriendException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum ApplicationStatus {

    APPROVED("Y"),
    REJECTED("N");

    private String message;

    ApplicationStatus(String message){
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }

    @JsonCreator
    public static ApplicationStatus fromValue(String value) {
        return Arrays.stream(ApplicationStatus.values())
                .filter(status -> status.message.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new AcceptInvalidFriendException());
    }
}
