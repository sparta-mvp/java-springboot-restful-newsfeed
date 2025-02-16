package com.example.newsfeed.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InterestTag interestTag;
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    protected User() {

    }

    public User(String name, String password, String email, InterestTag interestTag) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.interestTag = interestTag;
    }

    @PrePersist
    protected void prePersist() {
        this.status = UserStatus.ACTIVE;
    }


}
