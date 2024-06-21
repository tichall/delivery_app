package com.sparta.delivery_app.domain.order.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.OrderNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.UserOrderMismatchException;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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
            throw new UserOrderMismatchException(OrderErrorCode.USER_ORDER_MISMATCH);
        }
        return order;
    }
}
