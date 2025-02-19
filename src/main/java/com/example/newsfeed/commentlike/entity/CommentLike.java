package com.example.newsfeed.commentlike.entity;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment_like", uniqueConstraints = {
        @UniqueConstraint(
                name = "unique_comment_user",
                columnNames = {
                        "user_id",
                        "comment_id"
                }
        )
})
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    protected CommentLike() {

    }

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
