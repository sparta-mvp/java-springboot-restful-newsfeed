package com.example.newsfeed.commentlike.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class AlreadyLikedException extends NewsfeedAppException {
    public AlreadyLikedException() {
        super(ErrorCode.ALREADY_LIKED);
    }
}
