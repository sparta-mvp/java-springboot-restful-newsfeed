package com.example.newsfeed.comment.service.component;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentWriter {

    private final CommentRepository commentRepository;

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteComment(Long deleteId){
        commentRepository.deleteById(deleteId);
    }
}
