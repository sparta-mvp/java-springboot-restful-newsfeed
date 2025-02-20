package com.example.newsfeed.comment.dto;

import com.example.newsfeed.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {

    private final String writer;
    private final String postTitle;
    private final String contents;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime updatedAt;


    public static CommentResponse from(Comment entity) {
        return CommentResponse.builder()
                .writer(entity.getUser().getName())
                .postTitle(entity.getPost().getTitle())
                .contents(entity.getContents())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
