package com.sparta.delivery_app.domain.liked.entity;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLiked extends Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private UserReviews userReviews;

    @Builder
    public ReviewLiked(UserReviews userReviews, User user) {
        super(user);
        this.userReviews = userReviews;
    }
}
