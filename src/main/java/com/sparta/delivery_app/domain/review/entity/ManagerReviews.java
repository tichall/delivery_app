package com.sparta.delivery_app.domain.review.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewRequestDto;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "manager_reviews")
public class ManagerReviews extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_reviews_id")
    private Long id;

    @Column(name = "contnet", nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_reviews_id")
    private UserReviews userReviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ManagerReviewsStatus managerReviewsStatus;


    @Builder
    public ManagerReviews(Long id, String content, UserReviews userReviews, User user, ManagerReviewsStatus managerReviewsStatus) {
        this.id = id;
        this.content = content;
        this.userReviews = userReviews;
        this.user = user;
        this.managerReviewsStatus = managerReviewsStatus;
    }

    @Builder
    public static ManagerReviews of(UserReviews userReviews, User user, ManagerReviewRequestDto requestDto) {
        return ManagerReviews.builder()
                .content(requestDto.getContent())
                .userReviews(userReviews)
                .user(user)
                .managerReviewsStatus(ManagerReviewsStatus.ENABLE)
                .build();
    }

    @Builder
    public static ManagerReviews of(Long managerReviewId, ManagerReviewRequestDto requestDto) {
        return ManagerReviews.builder()
                .id(managerReviewId)
                .content(requestDto.getContent())
                .build();
    }
}
