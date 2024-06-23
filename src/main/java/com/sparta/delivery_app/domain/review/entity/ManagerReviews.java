package com.sparta.delivery_app.domain.review.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewRequestDto;
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

    @Column(nullable = false)
    private Long reviewsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ManagerReviewsStatus managerReviewsStatus;


    @Builder
    public ManagerReviews(Long id, String content, Long reviewsId, User user, ManagerReviewsStatus managerReviewsStatus) {
        this.id = id;
        this.content = content;
        this.reviewsId = reviewsId;
        this.user = user;
        this.managerReviewsStatus = managerReviewsStatus;
    }

    @Builder
    public static ManagerReviews of(Long userReviewId, User user, ManagerReviewRequestDto requestDto) {
        return ManagerReviews.builder()
                .content(requestDto.getContent())
                .reviewsId(userReviewId)
                .user(user)
                .managerReviewsStatus(ManagerReviewsStatus.ENABLE)
                .build();
    }
}
