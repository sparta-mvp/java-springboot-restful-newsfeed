package com.example.newsfeed.post.service;

import com.example.newsfeed.post.dto.PostResponse;

public interface PostService {
    PostResponse save(Long userId, String title, String contents, String keywords);
    PostResponse update(Long userId, Long id, String title, String contents, String keywords);
    void delete(Long userId, Long id);
}
