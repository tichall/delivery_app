package com.sparta.delivery_app.domain.review.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReviewsSearchCond {
    private Long likedUserId;
}
