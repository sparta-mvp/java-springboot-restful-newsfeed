package com.example.newsfeed.user.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class InvalidPasswordException extends NewsfeedAppException {

    public InvalidPasswordException( ) {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
