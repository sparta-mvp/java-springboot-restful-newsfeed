package com.example.newsfeed.post_like.repository;

import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post_like.entity.PostLike;
import com.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndUser(Post post, User user);

    long countPostLikeByPost(Post post);
}
