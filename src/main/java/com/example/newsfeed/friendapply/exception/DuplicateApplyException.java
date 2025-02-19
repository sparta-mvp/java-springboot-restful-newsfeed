package com.example.newsfeed.friendapply.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class DuplicateApplyException extends NewsfeedAppException {
    public DuplicateApplyException(){
        super(ErrorCode.DUPLICATE_APPLY);
    }
}
