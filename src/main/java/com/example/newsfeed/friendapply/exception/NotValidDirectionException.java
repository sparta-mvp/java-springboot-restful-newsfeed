package com.example.newsfeed.friendapply.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class NotValidDirectionException extends NewsfeedAppException {
    public NotValidDirectionException() {
        super(ErrorCode.INVALID_DIRECTION);
    }
}
