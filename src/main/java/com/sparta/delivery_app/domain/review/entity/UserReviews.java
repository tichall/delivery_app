package com.sparta.delivery_app.domain.review.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
}
