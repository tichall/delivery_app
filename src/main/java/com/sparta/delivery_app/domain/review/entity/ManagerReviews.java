package com.sparta.delivery_app.domain.review.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewModifyRequestDto;
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

    public static ManagerReviews saveManagerReview(UserReviews userReviews, User user, ManagerReviewAddRequestDto requestDto) {
        return ManagerReviews.builder()
                .content(requestDto.content())
                .userReviews(userReviews)
                .user(user)
                .managerReviewsStatus(ManagerReviewsStatus.ENABLE)
                .build();
    }

    public ManagerReviews modifyManagerReview(ManagerReviewModifyRequestDto requestDto) {
        this.content = requestDto.content();
        return this;
    }
}
