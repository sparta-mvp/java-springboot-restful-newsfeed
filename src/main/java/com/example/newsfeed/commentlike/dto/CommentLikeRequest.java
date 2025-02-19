package com.example.newsfeed.commentlike.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentLikeRequest {

    @NotNull(message = "댓글 아이디를 입력해야합니다.")
    private Long commentId;
}
