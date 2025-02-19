package com.example.newsfeed.friendapply.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class NotFoundApplyException extends NewsfeedAppException {
    public NotFoundApplyException(){
        super(ErrorCode.APPLY_NOT_FOUND);
    }
}
