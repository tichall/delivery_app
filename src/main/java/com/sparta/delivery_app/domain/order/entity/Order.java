package com.sparta.delivery_app.domain.order.entity;


import com.sparta.delivery_app.domain.commen.BaseTimeCreateEntity;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseTimeCreateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private UserReviews userReviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Builder
    public Order(User user, Store store, OrderStatus orderStatus) {
        this.user = user;
        this.store = store;
        this.orderStatus = orderStatus;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
    }

    public Long calculateTotalPrice() {
        Long totalPrice = 0L;
        for (OrderItem orderItem : this.orderItemList) {
            Long menuPrice = orderItem.getPriceAtTime();
            Integer quantity = orderItem.getQuantity();

            totalPrice += (menuPrice * quantity);
        }
        return totalPrice;
    }
}

