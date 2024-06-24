package com.sparta.delivery_app.domain.order.adapter;

import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemAdapter {

    private final OrderItemRepository orderItemRepository;

    public Page<OrderItem> findAll(Pageable pageable) {
        return orderItemRepository.findAll(pageable);
    }

}
