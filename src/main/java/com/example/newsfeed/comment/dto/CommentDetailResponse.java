package com.example.newsfeed.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDetailResponse {

    private final String writer;
    private final String postTitle;
    private final String contents;
    private final Long likeCount;
    private final boolean isLiked;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime updatedAt;

    public static CommentDetailResponse of(String writer, String postTitle, String contents, Long likeCount, boolean isLiked, LocalDateTime updatedAt) {
        return CommentDetailResponse.builder()
                .writer(writer)
                .postTitle(postTitle)
                .contents(contents)
                .likeCount(likeCount)
                .isLiked(isLiked)
                .updatedAt(updatedAt)
                .build();
    }

}
