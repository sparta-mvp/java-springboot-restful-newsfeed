package com.example.newsfeed.bookmark.dto;

import lombok.Getter;

@Getter
public class BookmarkRequest {
    private final Long id;
    private final Long postId;
    private final Long userId;

    public BookmarkRequest(Long id, Long postId, Long userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }

}
