package com.example.newsfeed.friend.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class NotFriendException extends NewsfeedAppException {
    public NotFriendException() {
        super(ErrorCode.NOT_FRIEND);
    }
}
