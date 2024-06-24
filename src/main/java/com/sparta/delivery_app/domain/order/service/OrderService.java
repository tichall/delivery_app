package com.sparta.delivery_app.domain.order.service;

import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.TotalPriceException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adapter.MenuAdapter;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.menu.service.MenuService;
import com.sparta.delivery_app.domain.order.adapter.OrderAdapter;
import com.sparta.delivery_app.domain.order.dto.request.MenuItemRequestDto;
import com.sparta.delivery_app.domain.order.dto.request.OrderAddRequestDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderAddResponseDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderGetResponseDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderPageResponseDto;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderAdapter orderAdapter;
    private final UserAdapter userAdapter;
    private final StoreAdapter storeAdapter;
    private final MenuAdapter menuAdapter;
    private final MenuService menuService;

    /**
     * 주문 생성
     * @param user 인증된 유저 정보
     * @param requestDto 주문 정보
     * @return OrderAddResponseDto 생성된 주문 정보
     */
    @Transactional
    public OrderAddResponseDto addOrder(AuthenticationUser user, final OrderAddRequestDto requestDto) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        Store store = storeAdapter.queryStoreById(requestDto.storeId());
        Long totalPrice;

        Order currentOrder = Order.saveOrder(findUser, store);

        addValidatedMenuItemsToOrder(currentOrder, requestDto.menuList());
        totalPrice = currentOrder.calculateTotalPrice();

        if (totalPrice < store.getMinTotalPrice()) {
            throw new TotalPriceException(OrderErrorCode.TOTAL_PRICE_ERROR);
        }

        orderAdapter.saveOrder(currentOrder);
        return OrderAddResponseDto.of(currentOrder, totalPrice);
    }

    /**
     * 주문 단건 조회
     * @param user 인증된 유저 정보
     * @param orderId 조회할 주문 아이디
     * @return OrderGetResponseDto 조회된 주문 정보
     */
    public OrderGetResponseDto findOrder(AuthenticationUser user, final Long orderId) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        Order findOrder = orderAdapter.queryOrderByIdAndUserId(findUser.getId(), orderId);
        return OrderGetResponseDto.of(findOrder);
    }

    /**
     * 주문 전체 조회 (페이징)
     * @param user 인증된 유저 정보
     * @param pageNum 접근할 페이지 번호
     * @param isDesc 내림차순 여부
     * @return OrderPageResponseDto 조회된 페이지
     */
    public OrderPageResponseDto findOrders(
            AuthenticationUser user,
            final Integer pageNum,
            final Boolean isDesc
    ) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);

        Page<Order> orderPage = orderAdapter.queryOrdersByUserId(pageable, findUser.getId());

        PageUtil.validatePage(pageNum, orderPage);
        return OrderPageResponseDto.of(pageNum, orderPage);
    }

    /**
     * 검증 없이 주문 상태를 배달 완료로 변경
     */
    @Transactional
    public void changeStatus(final Long orderId) {
        Order findOrder = orderAdapter.queryOrderById(orderId);
        findOrder.changeOrderStatus(OrderStatus.DELIVERY_COMPLETED);
    }

    /**
     * 주문 상태를 조리중으로 변경
     */
    @Transactional
    public void changeStatusPrepare(final Long orderId, AuthenticationUser user) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        storeAdapter.queryStoreId(findUser);
        Order findOrder = orderAdapter.queryOrderById(orderId);
        findOrder.changeOrderStatus(OrderStatus.IN_PREPARATION);
    }

    /**
     * 주문 상태를 배달 완료로 변경
     */
    @Transactional
    public void changeStatusDelivered(final Long orderId, AuthenticationUser user) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        storeAdapter.queryStoreId(findUser);
        Order findOrder = orderAdapter.queryOrderById(orderId);
        findOrder.changeOrderStatus(OrderStatus.DELIVERY_COMPLETED);
    }

    /**
     * 선택한 메뉴들을 현재 주문에 추가
     * @param currentOrder 현재 주문
     * @param menuItemRequestDtoList 선택한 메뉴
     */
    private void addValidatedMenuItemsToOrder(Order currentOrder, List<MenuItemRequestDto> menuItemRequestDtoList) {
        for (MenuItemRequestDto menuItemRequestDto : menuItemRequestDtoList) {
            Long menuId = menuItemRequestDto.menuId();
            Integer quantity = menuItemRequestDto.quantity();

            Menu menu = menuAdapter.queryMenuById(menuId);

            menu.checkStoreMenuMatch(menu, currentOrder.getStore().getId());

            MenuStatus.checkMenuStatus(menu);

            OrderItem orderItem = OrderItem.saveOrderItem(currentOrder, menu, quantity);

            currentOrder.addOrderItem(orderItem);
        }
    }
}
