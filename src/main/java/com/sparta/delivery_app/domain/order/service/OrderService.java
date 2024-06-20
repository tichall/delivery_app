package com.sparta.delivery_app.domain.order.service;

import com.sparta.delivery_app.common.exception.errorcode.MenuErrorCode;
import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.MenuStatusException;
import com.sparta.delivery_app.common.globalcustomexception.StoreMenuMismatchException;
import com.sparta.delivery_app.common.globalcustomexception.TotalPriceException;
import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.order.adaptor.OrderAdaptor;
import com.sparta.delivery_app.domain.order.dto.request.MenuItemRequestDto;
import com.sparta.delivery_app.domain.order.dto.request.OrderAddRequestDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderAddResponseDto;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderAdaptor orderAdaptor;
    private StoreAdaptor storeAdaptor;
    private MenuAdaptor menuAdaptor;

    public OrderAddResponseDto addOrder(OrderAddRequestDto requestDto) {
        Store store = storeAdaptor.queryStoreById(requestDto.getStoreId());
        Long totalPrice;

        Order currentOrder = Order.builder()
//                .user(user)
                .store(store)
                .orderStatus(OrderStatus.ORDER_COMPLETED)
                .build();

        addValidatedMenuItemsToOrder(currentOrder, requestDto.getMenuList());
        totalPrice = calculateTotalPrice(currentOrder);

        if (totalPrice < store.getMinTotalPrice()) {
            throw new TotalPriceException(OrderErrorCode.TOTAL_PRICE_ERROR);
        }

        orderAdaptor.saveOrder(currentOrder);
        return OrderAddResponseDto.of(currentOrder, totalPrice);
    }

    private void addValidatedMenuItemsToOrder(Order currentOrder, List<MenuItemRequestDto> menuItemRequestDtoList) {
        for (MenuItemRequestDto menuItemRequestDto : menuItemRequestDtoList) {
            Long menuId = menuItemRequestDto.getMenuId();
            Integer quantity = menuItemRequestDto.getQuantity();

            Menu menu = menuAdaptor.queryMenuById(menuId);

            if (menu.getStore() != currentOrder.getStore()) {
                throw new StoreMenuMismatchException(OrderErrorCode.STORE_MENU_MISMATCH);
            }

            // Menu 쪽에 로직을 추가하고 불러다 쓰는 게 더 나아보임
            if (menu.getMenuStatus().equals(MenuStatus.DISABLE)) {
                throw new MenuStatusException(MenuErrorCode.MENU_STATUS_ERROR);
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(currentOrder)
                    .menu(menu)
                    .quantity(quantity)
                    .build();

            currentOrder.addOrderItem(orderItem);
        }
    }

    private Long calculateTotalPrice(Order currentOrder) {
        Long totalPrice = 0L;
        for (OrderItem orderItem : currentOrder.getOrderItemList()) {
            Long menuPrice = orderItem.getMenu().getMenuPrice();
            Integer quantity = orderItem.getQuantity();

            totalPrice += (menuPrice * quantity);
        }
        return totalPrice;
    }
}
