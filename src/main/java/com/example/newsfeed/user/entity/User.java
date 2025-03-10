package com.example.newsfeed.user.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public boolean isSame(Long userId) {
        return Objects.equals(this.id, userId);
    }

    public void deActivate() {
        this.name = "탈퇴한 회원";
        this.status = UserStatus.DEACTIVATED;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean isDeactivated() {
        return this.status == UserStatus.DEACTIVATED;
    }

    public void updateInterestTag(InterestTag interestTag) {
        this.interestTag = interestTag;
    }

    public void updateName(String name) {
        this.name = name;
    }

    @PrePersist
    protected void prePersist() {
        this.status = UserStatus.ACTIVE;
    }

}
