package com.example.newsfeed.comment.dto;

import com.example.newsfeed.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {

    private final String writer;
    private final String postTitle;
    private final String contents;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime updatedAt;


    public static CommentResponseDto from(Comment entity){
        return CommentResponseDto.builder()
                .writer(entity.getUser().getName())
                .postTitle(entity.getPost().getTitle())
                .contents(entity.getContents())
                .updateAt(entity.getUpdatedAt())
                .build();
    }
}
