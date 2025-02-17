package com.example.newsfeed.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequest {

    @NotBlank(message = "제목은 필수로 입력되어야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수로 입력되어야 합니다.")
    private String contents;

    private String keywords;

    public PostRequest(String title, String contents, String keywords) {
        this.title = title;
        this.contents = contents;
        this.keywords = keywords;
    }
}
