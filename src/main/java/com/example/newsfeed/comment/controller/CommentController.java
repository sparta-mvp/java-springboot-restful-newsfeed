package com.example.newsfeed.comment.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.comment.dto.CommentDetailResponse;
import com.example.newsfeed.comment.dto.CommentRequest;
import com.example.newsfeed.comment.dto.CommentResponse;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.common.request.PageRequest;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<Response<CommentResponse>> addComment(@RequestParam Long postId,
                                                                @Valid @RequestBody CommentRequest requestDto,
                                                                @Login LoginUser loginUser) {

        CommentResponse comment = commentService.addComment(postId, loginUser, requestDto.getContents());
        return new ResponseEntity<>(Response.of(comment), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Response<CommentDetailResponse>> findByPostIdToComments(@Login(required = false) LoginUser loginUser, @RequestParam Long postId,
                                                                                  @Valid @ModelAttribute PageRequest page) {
        Page<CommentDetailResponse> commentsList = commentService.findByPostIdToComments(loginUser, postId, page.getSize(), page.getPage());
        return new ResponseEntity<>(Response.fromPage(commentsList), HttpStatus.OK);
    }


    @PutMapping("/{commentId}")
    public ResponseEntity<Response<CommentResponse>> updateComment(@PathVariable Long commentId,
                                                                   @Valid @RequestBody CommentRequest requestDto,
                                                                   @Login LoginUser loginUser) {

        CommentResponse updateComment = commentService.updateComment(commentId, loginUser, requestDto.getContents());
        return new ResponseEntity<>(Response.of(updateComment), HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")

    public ResponseEntity<Response<CommentResponse>> deleteComment(@PathVariable Long commentId, @Login LoginUser loginUser) {
        commentService.deleteComment(commentId, loginUser);
        return new ResponseEntity<>(Response.empty(), HttpStatus.NO_CONTENT);
    }
}
