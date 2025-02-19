package com.example.newsfeed.commentlike.service.component;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.commentlike.entity.CommentLike;
import com.example.newsfeed.commentlike.repository.CommentLikeRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeWriter {

    private final CommentLikeRepository commentLikeRepository;

    public void create(User user, Comment comment) {
        CommentLike commentLike = new CommentLike(user, comment);
        commentLikeRepository.save(commentLike);
    }

    public void delete(CommentLike commentLike) {
        commentLikeRepository.delete(commentLike);
    }
}
