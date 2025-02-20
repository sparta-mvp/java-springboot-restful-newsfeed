package com.example.newsfeed.commentlike.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.exception.CommentNotFoundIdException;
import com.example.newsfeed.comment.service.component.CommentFinder;
import com.example.newsfeed.comment.service.component.CommentReader;
import com.example.newsfeed.commentlike.entity.CommentLike;
import com.example.newsfeed.commentlike.dto.CommentLikeResponse;
import com.example.newsfeed.commentlike.exception.AlreadyLikedException;
import com.example.newsfeed.commentlike.exception.SelfLikeException;
import com.example.newsfeed.commentlike.service.component.CommentLikeFinder;
import com.example.newsfeed.commentlike.service.component.CommentLikeReader;
import com.example.newsfeed.commentlike.service.component.CommentLikeWriter;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final UserFinder userFinder;
    private final CommentFinder commentFinder;
    private final CommentReader commentReader;
    private final CommentLikeReader commentLikeReader;
    private final CommentLikeFinder commentLikeFinder;
    private final CommentLikeWriter commentLikeWriter;


    @Transactional
    public void likeComment(Long commentId, LoginUser loginUser) {
        Comment comment = commentFinder.getComment(commentId);

        if (comment.getUser().isSame(loginUser.getUserId())) {
            throw new SelfLikeException();
        }

        if (commentLikeReader.existsByCommentIdAndUserId(commentId, loginUser.getUserId())) {
            throw new AlreadyLikedException();
        }

        User user = userFinder.findById(loginUser.getUserId());

        commentLikeWriter.create(user, comment);
    }

    @Transactional
    public void unLikeComment(Long commentId, LoginUser loginUser) {

        Comment comment = commentFinder.getComment(commentId);

        if (comment.getUser().isSame(loginUser.getUserId())) {
            throw new SelfLikeException();
        }
        CommentLike commentLike = commentLikeFinder.findByCommentIdAndUserId(commentId, loginUser.getUserId());

        commentLikeWriter.delete(commentLike);

    }

    @Transactional(readOnly = true)
    public CommentLikeResponse getCommentLikeStatus(LoginUser loginUser, Long commentId) {
        if (!commentReader.exists(commentId)) {
            throw new CommentNotFoundIdException();
        }
        long likeCount = commentLikeReader.countByCommentId(commentId);
        boolean isLiked = commentLikeReader.existsByCommentIdAndUserId(commentId, loginUser.getUserId());
        return CommentLikeResponse.of(likeCount, isLiked);
    }
}
