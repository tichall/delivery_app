package com.sparta.delivery_app.domain.order.service;

import com.sparta.delivery_app.common.exception.errorcode.MenuErrorCode;
import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.MenuStatusException;
import com.sparta.delivery_app.common.globalcustomexception.StoreMenuMismatchException;
import com.sparta.delivery_app.common.globalcustomexception.TotalPriceException;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.order.adaptor.OrderAdaptor;
import com.sparta.delivery_app.domain.order.dto.request.MenuItemRequestDto;
import com.sparta.delivery_app.domain.order.dto.request.OrderAddRequestDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderAddResponseDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderGetResponseDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderPageResponseDto;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderAdaptor orderAdaptor;
    private StoreAdaptor storeAdaptor;
    private MenuAdaptor menuAdaptor;

    public OrderAddResponseDto addOrder(final OrderAddRequestDto requestDto) {
        Store store = storeAdaptor.queryStoreById(requestDto.storeId());
        Long totalPrice;

        Order currentOrder = Order.builder()
//                .user(user)
                .store(store)
                .orderStatus(OrderStatus.ORDER_COMPLETED)
                .build();

        addValidatedMenuItemsToOrder(currentOrder, requestDto.menuList());
        totalPrice = currentOrder.calculateTotalPrice();

        if (totalPrice < store.getMinTotalPrice()) {
            throw new TotalPriceException(OrderErrorCode.TOTAL_PRICE_ERROR);
        }

        orderAdaptor.saveOrder(currentOrder);
        return OrderAddResponseDto.of(currentOrder, totalPrice);
    }

    public OrderGetResponseDto findOrder(Long orderId) {
        // 해당 유저의 주문인지 검증 필요
        Long userId = 0L; // 임시
        Order order = orderAdaptor.queryOrderByIdAndUserID(userId, orderId);
        return OrderGetResponseDto.of(order);
    }

    public OrderPageResponseDto findOrders(Integer pageNum, String sortBy, Boolean isDesc) {
        Long userId = 0L; // 임시

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, sortBy, isDesc);

        Page<Order> orderPage = orderAdaptor.queryOrdersByUserId(pageable, userId);

        PageUtil.validatePage(pageNum, orderPage);
        return OrderPageResponseDto.of(pageNum, orderPage);
    }

    private void addValidatedMenuItemsToOrder(Order currentOrder, List<MenuItemRequestDto> menuItemRequestDtoList) {
        for (MenuItemRequestDto menuItemRequestDto : menuItemRequestDtoList) {
            Long menuId = menuItemRequestDto.menuId();
            Integer quantity = menuItemRequestDto.quantity();

            Menu menu = menuAdaptor.queryMenuById(menuId);

            if (menu.getStore() != currentOrder.getStore()) {
                throw new StoreMenuMismatchException(OrderErrorCode.STORE_MENU_MISMATCH);
            }

            MenuStatus.checkMenuStatus(menu);

            OrderItem orderItem = OrderItem.builder()
                    .order(currentOrder)
                    .menu(menu)
                    .quantity(quantity)
                    .build();

            currentOrder.addOrderItem(orderItem);
        }
    }

}
