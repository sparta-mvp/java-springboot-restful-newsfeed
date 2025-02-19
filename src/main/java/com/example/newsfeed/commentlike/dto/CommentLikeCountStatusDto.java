package com.example.newsfeed.commentlike.dto;

import lombok.Getter;


@Getter
public class CommentLikeCountStatusDto {

    private final Long commentId;
    private final Long count;
    private boolean isLiked;

    public CommentLikeCountStatusDto(Long commentId, Long count, boolean isLiked) {
        this.commentId = commentId;
        this.count = count;
        this.isLiked = isLiked;
    }

    public CommentLikeCountStatusDto(Long commentId, Long count) {
        this(commentId,count,false);
    }
}
