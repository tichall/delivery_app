package com.sparta.delivery_app.domain.review.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.order.entity.Order;
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
@Table(name = "user_reviews")
public class UserReviews extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_reviews_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    private String reviewImagePath;

    @Column(nullable = false)
    private int rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus reviewStatus;

    @Builder
    public UserReviews(String content, String reviewImagePath, int rating, Order order, User user, ReviewStatus reviewStatus) {
        this.content = content;
        this.reviewImagePath = reviewImagePath;
        this.rating = rating;
        this.order = order;
        this.user = user;
        this.reviewStatus = reviewStatus;
    }

    //Entity -> DTO로 변환
    @Builder
    public static UserReviews of(Order order, User user, UserReviewRequestDto requestDto) {
        return UserReviews.builder()
                .content(requestDto.getContent())
                .reviewImagePath(requestDto.getReviewImagePath())
                .rating(requestDto.getRating())
                .order(order)
                .user(user)
                .reviewStatus(ReviewStatus.ENABLE)
                .build();
    }

    public void updateReview(UserReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.reviewImagePath = requestDto.getReviewImagePath();
        this.rating = requestDto.getRating();
    }

    public void deleteReview() {
        this.reviewStatus = ReviewStatus.DISABLE;
    }
}
