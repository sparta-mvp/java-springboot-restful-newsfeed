package com.example.newsfeed.common.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> implements Response<T>{

    private final List<T> data;
    private final int pageNumber;
    private final int pageSize;
    private final int totalPages;
    private final long totalElements;

    public PageResponse(List<T> data, int pageNumber, int pageSize, int totalPages, long totalElements) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    @Override
    public T getData() {
        return (T) data;
    }
}
