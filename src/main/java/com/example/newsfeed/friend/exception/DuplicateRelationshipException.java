package com.example.newsfeed.friend.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class DuplicateRelationshipException extends NewsfeedAppException {

    public DuplicateRelationshipException() {
        super(ErrorCode.DUPLICATE_FRIEND);
    }
}
