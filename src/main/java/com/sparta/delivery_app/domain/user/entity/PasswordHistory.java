package com.sparta.delivery_app.domain.user.entity;

import com.sparta.delivery_app.domain.common.BaseTimeCreateEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "password_history")
public class PasswordHistory extends BaseTimeCreateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String password;

    @Builder
    public PasswordHistory(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public static PasswordHistory savePasswordHistory(User user, String encodePassword) {
        return PasswordHistory.builder()
                .user(user)
                .password(encodePassword)
                .build();
    }
}
