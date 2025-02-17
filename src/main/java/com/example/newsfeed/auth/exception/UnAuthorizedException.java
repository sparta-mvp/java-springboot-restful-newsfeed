package com.example.newsfeed.auth.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class UnAuthorizedException extends NewsfeedAppException {
    public UnAuthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
