package com.example.newsfeed.post.repository;

import com.example.newsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public interface PostRepository extends JpaRepository<Post, Long> {
    default Post findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));
    }

    @Query("SELECT p " +
            "FROM Post p WHERE p.title LIKE %:query% OR " +
            "p.contents LIKE %:query% OR " +
            "p.keyword LIKE %:query%")
    Page<Post> searchByAllFields(@Param("query") String query, Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByContentsContaining(String contents, Pageable pageable);

    Page<Post> findByKeywordContaining(String keyword, Pageable pageable);
}
