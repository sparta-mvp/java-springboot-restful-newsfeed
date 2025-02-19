package com.example.newsfeed.commentlike.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class AlreadyUnlikedException extends NewsfeedAppException {
    public AlreadyUnlikedException() {
        super(ErrorCode.ALREADY_UNLIKED);
    }
}
