package com.example.newsfeed.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequest {

    @NotBlank(message = "제목은 필수로 입력되어야 합니다.")
    @Size(max = 50)
    private final String title;

    @NotBlank(message = "내용은 필수로 입력되어야 합니다.")
    @Size(max = 1000)
    private final String contents;

    @Size(max = 100)
    private final String keywords;

    public PostRequest(String title, String contents, String keywords) {
        this.title = title;
        this.contents = contents;
        this.keywords = keywords;
    }
}
