package com.example.newsfeed.post.entity;

import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    private String keyword;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Post (){}

    public Post(String title, String contents, String keyword) {
        this.title = title;
        this.contents = contents;
        this.keyword = keyword;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updatePost(String title, String contents, String keyword) {
        this.title = title;
        this.contents = contents;
        this.keyword = keyword;
    }
}
