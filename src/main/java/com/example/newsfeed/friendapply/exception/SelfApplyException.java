package com.example.newsfeed.friendapply.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class SelfApplyException extends NewsfeedAppException {
    public SelfApplyException() {
        super(ErrorCode.SELF_APPLY);
    }
}
