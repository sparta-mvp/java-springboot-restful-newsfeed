package com.example.newsfeed.commentlike.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class SelfLikeException extends NewsfeedAppException {
    public SelfLikeException() {
        super(ErrorCode.SELF_LIKE_NOT_ALLOWED);
    }

}
