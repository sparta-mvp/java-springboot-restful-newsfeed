package com.example.newsfeed.comment.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class NoCommentToPostException extends NewsfeedAppException {
    public NoCommentToPostException(){
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
