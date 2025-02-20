package com.example.newsfeed.post_like.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class UnCheckedException extends NewsfeedAppException {
    public UnCheckedException() {
        super(ErrorCode.UNCHECKED);
    }
}
