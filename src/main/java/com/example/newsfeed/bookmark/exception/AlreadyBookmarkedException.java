package com.example.newsfeed.bookmark.exception;

import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.exception.NewsfeedAppException;

public class AlreadyBookmarkedException extends NewsfeedAppException {
    public AlreadyBookmarkedException() {
        super(ErrorCode.ALREADY_BOOKMARKED);
    }
}
