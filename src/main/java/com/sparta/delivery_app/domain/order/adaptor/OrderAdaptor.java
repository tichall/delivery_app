package com.sparta.delivery_app.domain.order.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.OrderAccessDeniedException;
import com.sparta.delivery_app.common.globalcustomexception.OrderNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.ReviewNotFoundException;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderAdaptor {

    private final OrderRepository orderRepository;

    @Transactional
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order queryOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(OrderErrorCode.ORDER_NOT_FOUND));
    }

    public Order queryOrderByIdAndUserID(Long userId, Long orderId) {
        Order order = queryOrderById(orderId);
        if (!order.getUser().getId().equals(userId)) {
            throw new OrderAccessDeniedException(OrderErrorCode.ORDER_ACCESS_DENIED);
        }
        return order;
    }

    public Page<Order> queryOrdersByUserId(Pageable pageable, Long userId) {
        return orderRepository.findAllByUserId(pageable, userId);
    }

    public List<Order> queryOrderListByStoreIdAndOrderStatus(Long storeId, OrderStatus orderStatus) {
        return orderRepository.findAllOrderByStoreIdAndOrderStatus(storeId, orderStatus);
    }

    public Long queryReviewIdByOrderId(Long orderId) {
        return orderRepository.findReviewIdById(orderId).orElseThrow(() ->
                new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW));
    }
}
