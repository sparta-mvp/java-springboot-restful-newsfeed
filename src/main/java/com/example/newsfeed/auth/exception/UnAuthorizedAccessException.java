package com.example.newsfeed.auth.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class UnAuthorizedAccessException extends NewsfeedAppException {
    public UnAuthorizedAccessException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS);
    }
}
