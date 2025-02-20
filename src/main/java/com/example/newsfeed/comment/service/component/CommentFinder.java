package com.example.newsfeed.comment.service.component;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.exception.CommentNotFoundIdException;
import com.example.newsfeed.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentFinder {
    private final CommentRepository commentRepository;


    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundIdException());
    }


    public Page<Comment> findCommentsWithPostAndUserByPostId(Long postId, Pageable pageable) {
        return commentRepository.findCommentsWithPostAndUserByPostId(postId, pageable);
    }
}
