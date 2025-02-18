package com.example.newsfeed.commentlike.service;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.commentlike.entity.CommentLike;
import com.example.newsfeed.comment.exception.CommentNotFoundIdException;
import com.example.newsfeed.commentlike.repository.CommentLikeRepository;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.exception.UserNotFoundException;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    //TODO 하위 서비스 의존하게 리팩토링
    //TODO Error처리 리팩토링
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public void likeComment(Long commentId, LoginUser loginUser) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundIdException());

        if (comment.getUser().isSame(loginUser.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 작성한 댓글에는 좋아요를 누를 수 없습니다.");
        }

        if (commentLikeRepository.existsByCommentIdAndUserId(commentId, loginUser.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다.");
        }

        User user = userRepository.findById(loginUser.getUserId()).orElseThrow(() -> new UserNotFoundException());


        CommentLike commentLike = new CommentLike(user, comment);
        commentLikeRepository.save(commentLike);
    }

    @Transactional
    public void unLikeComment(Long commentId, LoginUser loginUser) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundIdException());

        if (comment.getUser().isSame(loginUser.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 작성한 댓글에는 좋아요를 누를 수 없습니다.");
        }

        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, loginUser.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "좋아요를 누른적이 없습니다."));

        commentLikeRepository.delete(commentLike);
    }

}
