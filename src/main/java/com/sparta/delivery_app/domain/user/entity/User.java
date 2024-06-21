package com.sparta.delivery_app.domain.user.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickName;

    @Column(nullable = false)
    private String userAddress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserReviews> userReviewsList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ManagerReviews> managerReviewsList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @Builder
    public User(String email, String password, String name, String nickName, String userAddress, UserStatus userStatus, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.userAddress = userAddress;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }

    public void updateRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }

    public void updateUserStatus() {
        this.userStatus = UserStatus.DISABLE;
    }
}
