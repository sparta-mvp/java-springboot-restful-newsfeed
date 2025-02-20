package com.example.newsfeed.post_like.service.component;

import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post_like.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeReader {
    private final PostLikeRepository postLikeRepository;

    public long likeCountOfPost(Post post) {
        return postLikeRepository.countPostLikeByPost(post);
    }
}
