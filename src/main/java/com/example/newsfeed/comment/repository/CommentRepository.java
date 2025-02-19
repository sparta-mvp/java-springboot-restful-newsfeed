package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostId(Long postId, Pageable pageable);

    boolean existsById(Long id);

    boolean existsByPostId(Long postId);

    void deleteById(Long id);

    @Query("select c from Comment c join fetch c.post p join fetch c.user u where p.id = :postId")
    Page<Comment> findCommentsWithPostAndUserByPostId(Long postId, Pageable pageable);

    @Modifying
    @Query("delete from Comment c where c.post.id = :postId")
    void deleteByPostId(Long postId);
}
