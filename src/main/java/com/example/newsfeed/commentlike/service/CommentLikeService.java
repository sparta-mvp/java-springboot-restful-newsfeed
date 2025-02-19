package com.example.newsfeed.commentlike.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.commentlike.dto.CommentLikeResponse;
import com.example.newsfeed.commentlike.entity.CommentLike;
import com.example.newsfeed.comment.exception.CommentNotFoundIdException;
import com.example.newsfeed.commentlike.exception.AlreadLikedException;
import com.example.newsfeed.commentlike.exception.AlreadyUnlikedException;
import com.example.newsfeed.commentlike.exception.SelfLikeException;
import com.example.newsfeed.commentlike.repository.CommentLikeRepository;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    //TODO 하위 서비스 의존하게 리팩토링
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public void likeComment(Long commentId, LoginUser loginUser) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundIdException());

        if (comment.getUser().isSame(loginUser.getUserId())) {
            throw new SelfLikeException();
        }

        if (commentLikeRepository.existsByCommentIdAndUserId(commentId, loginUser.getUserId())) {
            throw new AlreadLikedException();
        }

        User user = userRepository.findById(loginUser.getUserId()).orElseThrow(() -> new UserNotFoundException());


        CommentLike commentLike = new CommentLike(user, comment);
        commentLikeRepository.save(commentLike);
    }

    @Transactional
    public void unLikeComment(Long commentId, LoginUser loginUser) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundIdException());

        if (comment.getUser().isSame(loginUser.getUserId())) {
            throw new SelfLikeException();
        }

        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, loginUser.getUserId())
                .orElseThrow(() -> new AlreadyUnlikedException());

        commentLikeRepository.delete(commentLike);
    }

    public CommentLikeResponse getCommentLikeStatus(LoginUser loginUser, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundIdException());
        long likeCount = commentLikeRepository.countByCommentId(commentId);
        boolean isLiked = commentLikeRepository.existsByCommentIdAndUserId(commentId, loginUser.getUserId());
        return CommentLikeResponse.of(likeCount, isLiked);
    }
}
