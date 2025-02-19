package com.example.newsfeed.commentlike.service.component;

import com.example.newsfeed.commentlike.entity.CommentLike;
import com.example.newsfeed.commentlike.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeReader {
    private final CommentLikeRepository commentLikeRepository;
    public boolean existsByCommentIdAndUserId(Long commentId, Long userId) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId, userId);
    }

    public Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId) {
        return commentLikeRepository.findByCommentIdAndUserId(commentId, userId);
    }

    public Long countByCommentId(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }
}
