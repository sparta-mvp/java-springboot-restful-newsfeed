package com.example.newsfeed.comment.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.comment.dto.CommentRequest;
import com.example.newsfeed.comment.dto.CommentResponse;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.common.resolvers.Login;

import com.example.newsfeed.common.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/post/{postId}")
    public ResponseEntity<Response<CommentResponse>> addComment(@PathVariable Long postId,
                                                                @Valid @RequestBody CommentRequest requestDto,
                                                                @Login LoginUser loginUser) {

        CommentResponse comment = commentService.addComment(postId, loginUser, requestDto.getContents());
        return new ResponseEntity<>(Response.of(comment), HttpStatus.CREATED);
    }


    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<Response<CommentResponse>> findByPostIdToComments(@PathVariable Long postId,
                                                                            @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        Page<CommentResponse> commentsList = commentService.findByPostIdToComments(postId, page);
        return new ResponseEntity<>(Response.fromPage(commentsList), HttpStatus.OK);
    }


    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Response<CommentResponse>> updateComment(@PathVariable Long commentId,
                                                                   @Valid @RequestBody CommentRequest requestDto,
                                                                   @Login LoginUser loginUser) {

        CommentResponse updateComment = commentService.updateComment(commentId, loginUser, requestDto.getContents());
        return new ResponseEntity<>(Response.of(updateComment), HttpStatus.OK);
    }


    @DeleteMapping("/comment/{commentId}")

    public ResponseEntity<Response<CommentResponse>> deleteComment(@PathVariable Long commentId, @Login LoginUser loginUser) {
        commentService.deleteComment(commentId, loginUser);
        return new ResponseEntity<>(Response.empty(), HttpStatus.NO_CONTENT);
    }
}
