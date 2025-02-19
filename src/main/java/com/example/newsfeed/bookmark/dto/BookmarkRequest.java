package com.example.newsfeed.bookmark.dto;

import lombok.Getter;

@Getter
public class BookmarkRequest {
    private final Long postId;
    private final Long userId;

    public BookmarkRequest( Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

}
