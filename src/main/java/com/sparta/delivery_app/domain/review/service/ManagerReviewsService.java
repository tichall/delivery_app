package com.sparta.delivery_app.domain.review.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.order.adapter.OrderAdapter;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.review.adapter.ManagerReviewsAdapter;
import com.sparta.delivery_app.domain.review.adapter.UserReviewsAdapter;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.ManagerReviewResponseDto;
import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerReviewsService {

    private final ManagerReviewsAdapter managerReviewsAdapter;
    private final OrderAdapter orderAdapter;
    private final UserReviewsAdapter userReviewsAdapter;
    private final UserAdapter userAdapter;


    public ManagerReviewResponseDto addReview(Long orderId, ManagerReviewRequestDto requestDto, AuthenticationUser user) {
        // 사용자 존재 확인
        User userData = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        // 주문 존재 확인
        Order orderData = orderAdapter.queryOrderById(orderId);

        // TODO
        UserReviews userReviews = orderData.getUserReviews();
        ManagerReviews managerReviews = userReviews.getManagerReviews();

        // 주문ID를 통해 리뷰ID 존재 확인
        Long userReviewId = orderAdapter.queryReviewIdByOrderId(orderData.getId());

        // 판매자리뷰가 이미 존재하는지 확인
        userReviewsAdapter.validateManagerReviewExistsByReviewId(userReviewId);

//        ManagerReviews managerReviews = ManagerReviews.of(userReviewId, userData, requestDto);

//        managerReviewsAdaptor.saveReview(managerReviews);

//        return ManagerReviewResponseDto.of(managerReviews);
        return null;
    }

    public ManagerReviewResponseDto modifyReview(Long orderId, ManagerReviewRequestDto requestDto, AuthenticationUser user) {
        // 사용자 존재 확인
        User userData = userAdapter.queryUserByEmail(user.getUsername());

        // 주문 존재 확인
        Order orderData = orderAdapter.queryOrderById(orderId);

        // Order를 통해 리뷰ID 존재 확인
        Long userReviewId = orderAdapter.queryReviewIdByOrderId(orderData.getId());

        // 판매자 리뷰가 존재하지않는지 확인
        Long managerReviewId = userReviewsAdapter.validateManagerReviewDoesNotExistByReviewId(userReviewId);

        ManagerReviews managerReviews = ManagerReviews.of(managerReviewId, requestDto);

        return ManagerReviewResponseDto.of(managerReviews);
    }

}
