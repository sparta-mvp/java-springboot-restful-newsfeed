package com.example.newsfeed.friend.entity;

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

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;

@Entity
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_friend", columnNames = {"to_user_id", "from_user_id"})
        }
)
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User fromUser;



    public Friend() {

    }


    public Friend(User toUser, User fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
    }

}
