package com.example.newsfeed.post.service;

import com.example.newsfeed.post.dto.PostResponse;
import com.example.newsfeed.post.dto.PostShortResponse;

public interface PostService {
    PostResponse save(Long userId, String title, String contents, String keywords);
    PostShortResponse findAllPosts();
    PostResponse findPostById(Long id);
    PostResponse update(Long userId, Long id, String title, String contents, String keywords);
    void delete(Long userId, Long id);
}
