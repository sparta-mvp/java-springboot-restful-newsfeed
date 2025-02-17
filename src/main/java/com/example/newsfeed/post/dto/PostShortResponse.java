package com.example.newsfeed.post.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostShortResponse {
    @Size(max = 25)
    private final String title;
    @Size(max = 100)
    private final String contents;
    private final String memberName;
    private final String keywords;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostShortResponse(String title, String contents, String memberName,
                             String keywords, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.contents = contents;
        this.memberName = memberName;
        this.keywords = keywords;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
