package com.example.newsfeed.post.service.component;

import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.exception.PostNotFoundException;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostFinder {
    private final PostRepository postRepository;

    @Transactional
    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException());
    }

    public Page<Post> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
