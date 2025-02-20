package com.example.newsfeed.post.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    private String keyword;

    public Post() {
    }

    public Post(String title, String contents, String keyword) {
        this.title = title;
        this.contents = contents;
        this.keyword = keyword;
    }

    public void updatePost(String title, String contents, String keyword) {
        this.title = title;
        this.contents = contents;
        this.keyword = keyword;
    }
}
