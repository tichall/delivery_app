package com.sparta.delivery_app.domain.review.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.order.adaptor.OrderAdaptor;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.review.adaptor.ManagerReviewsAdaptor;
import com.sparta.delivery_app.domain.review.adaptor.UserReviewsAdaptor;
import com.sparta.delivery_app.domain.review.dto.request.MangerReviewRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.ManagerReviewResponseDto;
import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MangerReviewsService {

    private final ManagerReviewsAdaptor managerReviewsAdaptor;
    private final OrderAdaptor orderAdaptor;
    private final UserReviewsAdaptor userReviewsAdaptor;
    private final UserAdaptor userAdaptor;


    public ManagerReviewResponseDto addReview(Long orderId, MangerReviewRequestDto requestDto, AuthenticationUser user) {

        // 사용자 존재 확인
        User userData = userAdaptor.queryUserByEmail(user.getUsername());

        // 주문 존재 확인
        Order orderData = orderAdaptor.queryOrderById(orderId);

        // 주문ID를 통해 리뷰ID 존재 확인
        Long userReviewId = orderAdaptor.queryReviewIdByOrderId(orderData.getId());

        // 판매자리뷰가 이미 존재하는지 확인
        userReviewsAdaptor.CheckManagerReviewIdByReviewId(userReviewId);

        ManagerReviews managerReviews = ManagerReviews.of(userReviewId, userData, requestDto);

        managerReviewsAdaptor.saveReview(managerReviews);

        return ManagerReviewResponseDto.of(managerReviews);
    }
}
