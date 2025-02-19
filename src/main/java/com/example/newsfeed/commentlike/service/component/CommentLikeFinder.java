package com.example.newsfeed.commentlike.service.component;

import com.example.newsfeed.commentlike.entity.CommentLike;
import com.example.newsfeed.commentlike.exception.CommentLikedNotFoundException;
import com.example.newsfeed.commentlike.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeFinder {

    private final CommentLikeRepository commentLikeRepository;

    public CommentLike findByCommentIdAndUserId(Long commentId, Long userId) {
        return commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(() -> new CommentLikedNotFoundException());
    }

}
