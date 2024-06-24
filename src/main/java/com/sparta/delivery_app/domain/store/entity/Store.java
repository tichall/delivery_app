package com.sparta.delivery_app.domain.store.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.store.dto.request.ModifyStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store")
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String storeName;

    @Column(nullable = false)
    private String storeAddress;

    @Column
    private String storeInfo;

    @Column(nullable = false, length = 12)
    private String storeRegistrationNumber;

    @Column(nullable = false)
    private Long minTotalPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStatus status;

    @Builder
    public Store(User user, String storeName, String storeAddress, String storeInfo, String storeRegistrationNumber, Long minTotalPrice, StoreStatus status) {
        this.user = user;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeInfo = storeInfo;
        this.storeRegistrationNumber = storeRegistrationNumber;
        this.minTotalPrice = minTotalPrice;
        this.status = status;
    }

    @Builder
    public static Store of (final RegisterStoreRequestDto requestDto, User user) {
        return Store.builder()
                .storeName(requestDto.storeName())
                .storeAddress(requestDto.storeAddress())
                .storeInfo(requestDto.storeInfo())
                .storeRegistrationNumber(requestDto.storeRegistrationNumber())
                .minTotalPrice(requestDto.minTotalPrice())
                .user(user)
                .status(StoreStatus.ENABLE)
                .build();
    }


    public void modifyStore(final ModifyStoreRequestDto requestDto) {
        this.storeName = requestDto.storeName();
        this.storeAddress = requestDto.storeAddress();
        this.storeRegistrationNumber = requestDto.storeRegistrationNumber();
        this.minTotalPrice = requestDto.minTotalPrice();
        this.storeInfo = requestDto.storeInfo();
    }
}
