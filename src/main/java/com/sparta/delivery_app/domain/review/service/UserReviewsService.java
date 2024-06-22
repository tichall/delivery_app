package com.sparta.delivery_app.domain.review.service;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.ReviewAccessDeniedException;
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

    public UserReviewResponseDto addReview(Long orderId, UserReviewRequestDto requestDto) {
        Order order = orderAdaptor.queryOrderById(orderId);
        User user = userAdaptor.getCurrentUser();
        UserReviews userReviews = UserReviews.of(order, user, requestDto);

        userReviewsAdaptor.saveReview(userReviews);

        return UserReviewResponseDto.of(userReviews);
    }

    @Transactional
    public UserReviewResponseDto modifyReview(Long reviewId, UserReviewRequestDto requestDto) {

        UserReviews userReviews = userReviewsAdaptor.checkValidReviewByIdAndReviewStatus(reviewId);
        User user = userAdaptor.getCurrentUser();

        if (!userReviews.getUser().getId().equals(user.getId())) {
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
    public void deleteReview(Long reviewId) {
        UserReviews userReviews = userReviewsAdaptor.checkValidReviewByIdAndReviewStatus(reviewId);

        User user = userAdaptor.getCurrentUser();

        if (!userReviews.getUser().getId().equals(user.getId())) {
            throw new ReviewAccessDeniedException(ReviewErrorCode.NOT_AUTHORITY_TO_DELETE_REVIEW);
        }

        userReviews.deleteReview();
    }
}
