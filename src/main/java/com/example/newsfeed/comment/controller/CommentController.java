package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.CommentRequestDto;
import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.common.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/post/{postId}")
    public ResponseEntity<Response<CommentResponseDto>> addComment(@PathVariable Long postId,
                                                                   @Valid @RequestBody CommentRequestDto requestDto,
                                                                   HttpServletRequest servletRequest){
        //TODO: 세션 받아오는 기능 확인 후 변경 필요

        CommentResponseDto comment = commentService.addComment(postId, session , requestDto.getContents());
        return new ResponseEntity<>(Response.of(comment), HttpStatus.CREATED);
    }


    //TODO: 페이징 관련 사용 확인 -> fromPage() 사용x
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<Response<CommentResponseDto>> findByPostIdToComments(@PathVariable Long postId,
                                                                                     @RequestParam(required = false, defaultValue = "0", value = "page") int page){
        Page<CommentResponseDto> commentsList = commentService.findByPostIdToComments(postId, page);
        return new ResponseEntity<>(Response.fromPage(commentsList), HttpStatus.OK);
    }


    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Response<CommentResponseDto>> updateComment(@PathVariable Long commentId,
                                                                      @Valid @RequestBody CommentRequestDto requestDto,
                                                                      HttpServletRequest servletRequest){

        //TODO: 세션 받아오는 기능 확인 후 변경 필요

        CommentResponseDto updateComment = commentService.updateComment(commentId, session, requestDto.getContents());
        return new ResponseEntity<>(Response.of(updateComment), HttpStatus.OK);
    }


    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Response<CommentResponseDto>> deleteComment(@PathVariable Long commentId, HttpServletRequest servletRequest){

        //TODO: 세션 받아오는 기능 확인 후 변경 필요

        commentService.deleteComment(commentId, session);
        return new ResponseEntity<>(Response.empty(), HttpStatus.NO_CONTENT);
    }
}
