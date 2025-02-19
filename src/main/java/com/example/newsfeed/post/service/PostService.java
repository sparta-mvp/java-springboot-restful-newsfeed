package com.example.newsfeed.post.service;

import com.example.newsfeed.post.dto.PostLoggedResponse;
import com.example.newsfeed.post.dto.PostResponse;
import com.example.newsfeed.post.dto.PostShortResponse;
import com.example.newsfeed.post.entity.SearchType;
import org.springframework.data.domain.Page;

public interface PostService {
    PostResponse save(Long userId, String title, String contents, String keywords);
    Page<PostShortResponse> findAllPosts(int pageSize, int pageNumber);
    Page<PostShortResponse> findAllWithLikeSorted(int pageSize, int pageNumber);
    PostResponse findPostById(Long id);
    PostResponse update(Long userId, Long id, String title, String contents, String keywords);
    void delete(Long userId, Long id);
    Page<PostShortResponse> findWithQuery(SearchType searchType, String query, int pageSize, int pageNumber);

}
