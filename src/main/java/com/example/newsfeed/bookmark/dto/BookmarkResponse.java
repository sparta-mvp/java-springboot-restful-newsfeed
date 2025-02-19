package com.example.newsfeed.bookmark.dto;

import lombok.Getter;

@Getter
public class BookmarkResponse {
    private final Long Id;
    private final Long userId;
    private final Long postId;

    public BookmarkResponse(Long Id, Long userId, Long postId) {
        this.Id = Id;
        this.userId = userId;
        this.postId = postId;
    }
}
