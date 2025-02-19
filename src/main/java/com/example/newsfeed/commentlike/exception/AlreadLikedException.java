package com.example.newsfeed.commentlike.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class AlreadLikedException extends NewsfeedAppException {
    public AlreadLikedException() {
        super(ErrorCode.ALREADY_LIKED);
    }
}
