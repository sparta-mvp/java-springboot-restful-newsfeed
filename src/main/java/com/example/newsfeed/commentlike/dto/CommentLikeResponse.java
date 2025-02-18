package com.example.newsfeed.commentlike.dto;

import lombok.Getter;

@Getter
public class CommentLikeResponse {
    private final Long likeCount;

    public CommentLikeResponse(Long likeCount) {
        this.likeCount = likeCount;
    }
}
