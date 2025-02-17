package com.example.newsfeed.post.service;

import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.entity.Post;

public interface PostService {
    PostResponseDto save(Long userId, String title, String contents, String keywords);
    PostResponseDto update(Long userId, Long id, String title, String contents, String keywords);
    void delete(Long userId, Long id);
}
