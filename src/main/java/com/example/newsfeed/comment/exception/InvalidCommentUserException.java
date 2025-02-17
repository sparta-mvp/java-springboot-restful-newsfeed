package com.example.newsfeed.comment.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class InvalidCommentUserException extends NewsfeedAppException {
    public InvalidCommentUserException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS);
    }
}
