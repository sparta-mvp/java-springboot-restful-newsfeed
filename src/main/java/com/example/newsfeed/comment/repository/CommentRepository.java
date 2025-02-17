package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.exception.CommentNotFoundIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostId(Long postId, Pageable pageable);

    Page<Comment> findByUserId(Long userId, Pageable pageable);

    default Comment findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow( () ->
                new CommentNotFoundIdException());
    }
}
