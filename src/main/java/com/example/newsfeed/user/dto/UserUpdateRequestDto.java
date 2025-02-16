package com.example.newsfeed.user.dto;

import com.example.newsfeed.common.validator.EnumValid;
import com.example.newsfeed.user.entity.InterestTag;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    private String name;

    @EnumValid(enummClass = InterestTag.class)
    private String interestTag;
}
