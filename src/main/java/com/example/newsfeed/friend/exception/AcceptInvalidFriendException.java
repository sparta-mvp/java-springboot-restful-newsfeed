package com.example.newsfeed.friend.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class AcceptInvalidFriendException extends NewsfeedAppException {
    public AcceptInvalidFriendException(){
        super(ErrorCode.INVALID_ACCEPT_FRIEND);
    }
}
