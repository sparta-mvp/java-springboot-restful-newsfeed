package com.example.newsfeed.post.dto;

import com.example.newsfeed.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private final String title;
    private final String contents;
    private final String memberName;
    private final String keywords;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponse(String title, String contents, String memberName,
                        String keywords, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.contents = contents;
        this.memberName = memberName;
        this.keywords = keywords;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PostResponse(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.memberName = post.getUser().getName();
        this.keywords = post.getKeyword();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
