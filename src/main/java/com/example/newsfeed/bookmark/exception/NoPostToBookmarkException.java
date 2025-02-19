package com.example.newsfeed.bookmark.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class NoPostToBookmarkException extends NewsfeedAppException {
    public NoPostToBookmarkException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
