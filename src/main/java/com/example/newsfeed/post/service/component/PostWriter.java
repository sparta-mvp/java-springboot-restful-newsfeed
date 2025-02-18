package com.example.newsfeed.post.service.component;

import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostWriter {
    private final PostRepository postRepository;
    // create
    public Post createPost(Post post) { return postRepository.save(post); }

    // delete
    public void deletePost(Post post) { postRepository.delete(post); }
}
