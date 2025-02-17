package com.example.newsfeed.user.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class DeActivatedUserException extends NewsfeedAppException {
    public DeActivatedUserException() {
        super(ErrorCode.DEACTIVATED_USER);
    }
}
