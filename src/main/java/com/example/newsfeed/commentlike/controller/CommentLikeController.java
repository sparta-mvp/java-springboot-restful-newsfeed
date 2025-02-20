package com.example.newsfeed.commentlike.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.commentlike.dto.CommentLikeRequest;
import com.example.newsfeed.commentlike.dto.CommentLikeResponse;
import com.example.newsfeed.commentlike.service.CommentLikeService;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment-likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public Response<Void> likeComment(@Login LoginUser loginUser, @RequestBody @Valid CommentLikeRequest commentLikeRequest) {
        commentLikeService.likeComment(commentLikeRequest.getCommentId(), loginUser);
        return Response.empty();
    }

    @DeleteMapping
    public Response<Void> unLikeComment(@Login LoginUser loginUser, @RequestParam Long commentId) {
        commentLikeService.unLikeComment(commentId, loginUser);
        return Response.empty();
    }

    @GetMapping
    public Response<CommentLikeResponse> getCommentLikeStatus(@Login(required = false) LoginUser loginUser, @RequestParam Long commentId) {
        return Response.of(commentLikeService.getCommentLikeStatus(loginUser, commentId));
    }

}
