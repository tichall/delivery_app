package com.sparta.delivery_app.domain.review.service;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.ReviewAccessDeniedException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.order.adaptor.OrderAdaptor;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.review.adaptor.UserReviewsAdaptor;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewResponseDto;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserReviewsService {

    private final UserReviewsAdaptor userReviewsAdaptor;
    private final OrderAdaptor orderAdaptor;
    private final UserAdaptor userAdaptor;

    public UserReviewResponseDto addReview(Long orderId, UserReviewRequestDto requestDto, AuthenticationUser user) {
        Order order = orderAdaptor.queryOrderById(orderId);
        User userData = userAdaptor.queryUserByEmail(user.getUsername());
        UserReviews userReviews = UserReviews.of(order, userData, requestDto);

        userReviewsAdaptor.saveReview(userReviews);

        return UserReviewResponseDto.of(userReviews);
    }

    @Transactional
    public UserReviewResponseDto modifyReview(Long reviewId, UserReviewRequestDto requestDto, AuthenticationUser user) {

        UserReviews userReviews = userReviewsAdaptor.checkValidReviewByIdAndReviewStatus(reviewId);
        User userData = userAdaptor.queryUserByEmail(user.getUsername());

        if (!userReviews.getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_UPDATE_REVIEW);
        }

        userReviews.updateReview(requestDto);

        return new UserReviewResponseDto(userReviews.getOrder().getStore().getStoreName(),
                userReviews.getUser().getNickName(),
                userReviews.getContent(),
                userReviews.getReviewImagePath(),
                userReviews.getRating());
    }

    @Transactional
    public void deleteReview(Long reviewId, AuthenticationUser user) {

        UserReviews userReviews = userReviewsAdaptor.checkValidReviewByIdAndReviewStatus(reviewId);
        User userData = userAdaptor.queryUserByEmail(user.getUsername());

        if (!userReviews.getUser().getId().equals(userData.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_DELETE_REVIEW);
        }

        userReviews.deleteReview();
    }
}
