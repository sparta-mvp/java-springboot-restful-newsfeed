package com.example.newsfeed.friendapply.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class FriendAlreadyExistsException extends NewsfeedAppException {
    public FriendAlreadyExistsException() {
        super(ErrorCode.FRIEND_ALREADY_EXISTS);
    }
}
