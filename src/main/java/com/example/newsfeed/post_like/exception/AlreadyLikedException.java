package com.example.newsfeed.post_like.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class AlreadyLikedException extends NewsfeedAppException {
    public AlreadyLikedException() {
        super(ErrorCode.ALREADY_LIKED_POST);
    }
}
