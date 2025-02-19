package com.example.newsfeed.bookmark.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "bookmark", uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_bookmark",
                columnNames = {
                        "bookmark_user_id",
                        "bookmark_post_id"
                }
        )
})
public class Bookmark extends BaseEntity {
    public Bookmark() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;  // BookMarkId (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_user_id", nullable = false)
    private User user;  // BookMarkUserId (유저 ID)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_post_id", nullable = false)
    private Post post;  // BookMarkPostId (저장한 게시글 ID)

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Bookmark(User user) {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Bookmark(User user, Post post) {
        this.user = user;
        this.post = post;
    }

}
