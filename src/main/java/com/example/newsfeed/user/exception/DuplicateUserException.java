package com.example.newsfeed.user.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;


public class DuplicateUserException extends NewsfeedAppException {
    public DuplicateUserException() {
        super(ErrorCode.DUPLICATE_MEMBER);
    }
}
