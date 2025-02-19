package com.example.newsfeed.post.dto;

import com.example.newsfeed.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostLoggedResponse {
    private final String title;
    private final String contents;
    private final String memberName;
    private final String keywords;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long likeCnt;
    private final boolean isChecked;

    public PostLoggedResponse(String title, String contents, String memberName, String keywords,
                        LocalDateTime createdAt, LocalDateTime updatedAt, long likeCnt, boolean isChecked) {
        this.title = title;
        this.contents = contents;
        this.memberName = memberName;
        this.keywords = keywords;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likeCnt = likeCnt;
        this.isChecked = isChecked;
    }

    public PostLoggedResponse(Post post, long likeCnt, boolean isChecked) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.memberName = post.getUser().getName();
        this.keywords = post.getKeyword();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.likeCnt = likeCnt;
        this.isChecked = isChecked;
    }
}
