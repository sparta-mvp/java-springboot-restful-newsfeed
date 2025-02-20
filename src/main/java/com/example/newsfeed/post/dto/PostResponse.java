package com.example.newsfeed.post.dto;

import com.example.newsfeed.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostResponse {
    private final String title;
    private final String contents;
    private final String memberName;
    private final String keywords;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long likeCnt;

    public PostResponse(String title, String contents, String memberName, String keywords,
                        LocalDateTime createdAt, LocalDateTime updatedAt, long likeCnt) {
        this.title = title;
        this.contents = contents;
        this.memberName = memberName;
        this.keywords = keywords;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likeCnt = likeCnt;
    }

    public PostResponse(Post post, long likeCnt) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.memberName = post.getUser().getName();
        this.keywords = post.getKeyword();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.likeCnt = likeCnt;
    }
}
