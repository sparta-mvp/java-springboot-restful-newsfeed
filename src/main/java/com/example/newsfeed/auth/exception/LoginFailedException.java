package com.example.newsfeed.auth.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;
import com.example.newsfeed.user.exception.UserNotFoundException;

public class LoginFailedException extends NewsfeedAppException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }

}
