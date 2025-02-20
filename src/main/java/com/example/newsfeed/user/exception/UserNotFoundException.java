package com.example.newsfeed.user.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class UserNotFoundException extends NewsfeedAppException {
    public UserNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
