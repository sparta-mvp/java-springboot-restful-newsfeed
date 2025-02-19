package com.example.newsfeed.bookmark.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class BookMarkNotFoundException extends NewsfeedAppException {
    public BookMarkNotFoundException() {
        super(ErrorCode.BOOKMARK_NOT_FOUND);
    }
}
