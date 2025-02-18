package com.example.newsfeed.friend.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class FriendNotFoundException extends NewsfeedAppException {
    public FriendNotFoundException() {
        super(ErrorCode.FRIEND_NOT_FOUND);
    }
}
