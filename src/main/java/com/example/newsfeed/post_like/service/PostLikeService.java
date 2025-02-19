package com.example.newsfeed.post_like.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.exception.ValidationException;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.service.component.PostFinder;
import com.example.newsfeed.post_like.entity.PostLike;
import com.example.newsfeed.post_like.exception.AlreadyLikedException;
import com.example.newsfeed.post_like.exception.UnCheckedException;
import com.example.newsfeed.post_like.repository.PostLikeRepository;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostFinder postFinder;
    private final UserFinder userFinder;

    public void likePost(LoginUser loginUser, Long postId) {
        Post post = postFinder.findPost(postId);
        User user = userFinder.findActive(loginUser.getUserId());

        // 중복 여부 확인
        Optional<PostLike> existing = postLikeRepository.findByPostAndUser(post, user);
        if(existing.isPresent()) { throw new AlreadyLikedException(); }

        if(post.getUser().equals(user)) throw new ValidationException();

        postLikeRepository.save(new PostLike(post, user));
    }

    public void unlikePost(LoginUser loginUser, Long postId){
        Post post = postFinder.findPost(postId);
        User user = userFinder.findActive(loginUser.getUserId());

        PostLike postLike = postLikeRepository.findByPostAndUser(post, user)
                .orElseThrow(() -> new UnCheckedException());

        postLikeRepository.delete(postLike);
    }
}
