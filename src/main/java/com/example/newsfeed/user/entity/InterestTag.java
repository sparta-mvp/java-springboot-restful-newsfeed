package com.example.newsfeed.user.entity;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.ValidationException;
import java.util.Arrays;

public enum InterestTag {
    FITNESS,
    TRAVEL,
    FOOD,
    GAMING,
    ART,
    FASHION;

    public static InterestTag of(String interestTag) {
        return Arrays.stream(InterestTag.values())
                .filter(tag -> tag.name().equals(interestTag))
                .findFirst()
                .orElseThrow(() -> new ValidationException(ErrorCode.INVALID_INTEREST_TAG));
    }
}
