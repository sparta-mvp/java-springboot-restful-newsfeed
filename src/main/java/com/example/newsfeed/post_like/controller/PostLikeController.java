package com.example.newsfeed.post_like.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.Response;
import com.example.newsfeed.post_like.service.PostLikeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping("/{postId}/like")
    public Response<Void> likePost(@Login LoginUser loginUser, @PathVariable Long postId) {
        postLikeService.likePost(loginUser, postId);
        return Response.empty();
    }

    @PostMapping("/{postId}/unlike")
    public Response<Void> unlikePost(@Login LoginUser loginUser, @PathVariable Long postId) {
        postLikeService.unlikePost(loginUser, postId);
        return Response.empty();
    }
}
