package com.example.newsfeed.comment.service;

import com.example.newsfeed.auth.dto.LoginUser;

import com.example.newsfeed.comment.dto.CommentResponse;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.exception.InvalidCommentUserException;
import com.example.newsfeed.comment.service.component.CommentFinder;
import com.example.newsfeed.comment.service.component.CommentReader;
import com.example.newsfeed.comment.service.component.CommentWriter;

import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentFinder commentFinder;
    private final CommentWriter commentWriter;

    private final UserFinder userFinder;


    public CommentResponse addComment(Long postId, LoginUser loginUser, String contents) {

        //TODO: post 정보 가져오기
        User user = userFinder.findActive(loginUser.getUserId());

        Comment save = commentWriter.saveComment(new Comment(user, null, contents));
        return CommentResponse.from(save);
    }


    public Page<CommentResponse> findByPostIdToComments(Long postId, int pageSize, int pageNumber) {
        //TODO: post 존재 확인

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Comment> commentList = commentFinder.getCommentsByPost(postId, pageable);

        return commentList.map(CommentResponse::from);
    }


    @Transactional
    public CommentResponse updateComment(Long id, LoginUser loginUser, String contents) {
        Comment findComment = commentFinder.getComment(id);

        if (!findComment.getUser().isSame(loginUser.getUserId())) {
            throw new InvalidCommentUserException();
        }

        Comment saveComment = findComment.update(contents);
        return CommentResponse.from(saveComment);
    }


    public void deleteComment(Long id, LoginUser loginUser) {
        Comment findComment = commentFinder.getComment(id);
        if (!findComment.getUser().isSame(loginUser.getUserId())) {
            throw new InvalidCommentUserException();
        }
        commentWriter.deleteComment(id);
    }

}
