package com.example.newsfeed.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NewsfeedAppException extends RuntimeException {

    private HttpStatus status;
    private String message;
    private String code;

    public NewsfeedAppException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.status = errorCode.getStatus();
        this.message = errorCode.getDefaultMessage();
        this.code = errorCode.getCode();
    }

    public NewsfeedAppException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getDefaultMessage(), cause);
        this.status = errorCode.getStatus();
        this.message = errorCode.getDefaultMessage();
        this.code = errorCode.getCode();
    }
}
