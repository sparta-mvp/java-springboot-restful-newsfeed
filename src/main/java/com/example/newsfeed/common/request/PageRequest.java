package com.example.newsfeed.common.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageRequest {

    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
    private int page;

    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    private int size;
}
