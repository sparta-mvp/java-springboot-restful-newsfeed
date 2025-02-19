package com.example.newsfeed.post_like.service.component;

import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post_like.entity.PostLike;
import com.example.newsfeed.post_like.repository.PostLikeRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeReader {
    private final PostLikeRepository postLikeRepository;

    public boolean likeActivate(Post post, User user){
        Optional<PostLike> existing = postLikeRepository.findByPostAndUser(post, user);

        return existing.isPresent();
    }

    public long likeCountOfPost(Post post) {
        return postLikeRepository.countPostLikeByPost(post);
    }
}
