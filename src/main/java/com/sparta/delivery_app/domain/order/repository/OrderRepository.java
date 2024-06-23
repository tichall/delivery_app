package com.sparta.delivery_app.domain.order.repository;

import com.sparta.delivery_app.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserId(Pageable pageable, Long userId);
    Optional<Long> findReviewIdByOrderId(Long orderId);
}
