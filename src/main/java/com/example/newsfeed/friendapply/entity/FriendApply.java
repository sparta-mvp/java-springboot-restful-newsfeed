package com.example.newsfeed.friendapply.entity;

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

@Entity
@Getter
@Table(name = "apply", uniqueConstraints = {
        @UniqueConstraint(name = "friend_apply", columnNames = {"sender_id", "receiver_id"})
})
public class FriendApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User receiver;

    public FriendApply() {
    }

    public FriendApply(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
