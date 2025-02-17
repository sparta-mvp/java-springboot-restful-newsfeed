package com.example.newsfeed.common.exception;

public class ValidationException extends NewsfeedAppException{
    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
