package com.example.newsfeed.common.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PageRequest {

    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
    private int page;

    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    private int size;

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
