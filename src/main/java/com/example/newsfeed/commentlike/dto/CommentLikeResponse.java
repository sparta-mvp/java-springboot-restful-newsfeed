package com.example.newsfeed.commentlike.dto;

import lombok.Getter;

@Getter
public class CommentLikeResponse {
    private final Long likeCount;
    private final boolean isLiked;

    private CommentLikeResponse(Long likeCount, boolean isLiked) {
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }

    public static CommentLikeResponse of(long likeCount, boolean isLiked) {
        return new CommentLikeResponse(likeCount, isLiked);
    }
}
