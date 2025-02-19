package com.example.newsfeed.bookmark.repository;

import com.example.newsfeed.bookmark.entity.Bookmark;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByUserAndPost(User user, Post post);

    List<Bookmark> findByUserId(Long userId);

    @Query("select b from Bookmark b join fetch b.user WHERE b.id = :id AND b.user.id = :userId")
    Optional<Bookmark> findByUserIdAndId(Long id, Long userId);
}
