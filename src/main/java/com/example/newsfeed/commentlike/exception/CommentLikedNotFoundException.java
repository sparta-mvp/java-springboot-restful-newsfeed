package com.example.newsfeed.commentlike.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class CommentLikedNotFoundException extends NewsfeedAppException {
    public CommentLikedNotFoundException() {
        super(ErrorCode.LIKE_NOT_FOUND);
    }
}
