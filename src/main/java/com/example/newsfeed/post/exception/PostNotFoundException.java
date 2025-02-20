package com.example.newsfeed.post.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class PostNotFoundException extends NewsfeedAppException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
