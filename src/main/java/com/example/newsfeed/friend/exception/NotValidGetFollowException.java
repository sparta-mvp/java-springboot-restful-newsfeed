package com.example.newsfeed.friend.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class NotValidGetFollowException extends NewsfeedAppException {
    public NotValidGetFollowException() {
        super(ErrorCode.INVALID_FOLLOW_TYPE);
    }
}
