package com.example.newsfeed.post.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;

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
    private final long likeCnt;

    public PostShortResponse(String title, String contents, String memberName,
                             String keywords, LocalDateTime createdAt, LocalDateTime updatedAt, long likeCnt) {
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.contents = contents.substring(0, Math.min(contents.length(), 50));
        this.memberName = memberName;
        this.keywords = keywords;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likeCnt = likeCnt;
    }
}
