package com.sparta.delivery_app.domain.order.repository;

import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.List;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserId(Pageable pageable, Long userId);

    List<Order> findAllOrderByStoreIdAndOrderStatus(Long storeId, OrderStatus orderStatus);

    Optional<Long> findReviewIdById(Long orderId);
}
