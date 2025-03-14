package com.example.newsfeed.comment.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Post post;

    @Column(nullable = false)
    private String contents;


    public Comment() {
    }

    public Comment(User user, Post post, String contents) {
        this.user = user;
        this.post = post;
        this.contents = contents;
    }

    public Comment update(String contents) {
        this.contents = contents;
        return this;
    }

}
