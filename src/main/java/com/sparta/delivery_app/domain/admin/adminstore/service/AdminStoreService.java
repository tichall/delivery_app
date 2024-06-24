package com.sparta.delivery_app.domain.admin.adminstore.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.admin.adminstore.dto.*;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adapter.MenuAdapter;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.order.adapter.OrderAdapter;
import com.sparta.delivery_app.domain.order.adapter.OrderItemAdapter;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.order.repository.OrderItemRepository;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sparta.delivery_app.domain.user.entity.UserStatus.checkManagerEnable;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStoreService {

    private final MenuAdapter menuAdapter;
    private final StoreAdapter storeAdapter;
    private final UserAdapter userAdapter;
    private final OrderAdapter orderAdapter;
    private final OrderItemAdapter orderItemAdapter;
    private final OrderItemRepository orderItemRepository;

    public PageMenuPerStoreResponseDto getMenuListPerStore(
            Long storeId, AuthenticationUser authenticationUser, final Integer pageNum,
            final Boolean isDesc) {
        log.info("getMenuListPerStore-service");
        //(ADMIN 권한의) 유저 Status 가 ENABLE 인지 확인
        adminUserStatusCheck(authenticationUser);

        Store choiceStore = storeAdapter.queryStoreById(storeId);
        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);

        Page<Menu> menuPage = menuAdapter.queryMenuListByStoreId(storeId, pageable);
        PageUtil.validatePage(pageNum, menuPage);

        return PageMenuPerStoreResponseDto.of(pageNum, choiceStore);
    }

    public PageReviewPerStoreResponseDto getReviewListPerStore(
            AuthenticationUser authenticationUser, Long storeId, final Integer pageNum,
            Boolean isDesc) {
        log.info("특정 매장 모든 리뷰 조회-service 시작");
        adminUserStatusCheck(authenticationUser);
        Store choiceStore = storeAdapter.queryStoreById(storeId);

        //storeId 와 OrderStatus.DELIVERY_COMPLETED 로 orderList 가져와서 하나씩 responseDto 에 담기
        List<Order> deliveredOrderList = orderAdapter.queryOrderListByStoreIdAndOrderStatus(storeId, OrderStatus.DELIVERY_COMPLETED);
        List<ReviewPerStoreResponseDto> reviewDtoList = new ArrayList<>();

        for (Order deliveredOrder : deliveredOrderList) {
            UserReviews userReview = deliveredOrder.getUserReviews();
            if (userReview != null) {
                ReviewPerStoreResponseDto ReviewResponseDto = ReviewPerStoreResponseDto.of(userReview);
                reviewDtoList.add(ReviewResponseDto);
            } else {
                log.warn("Order ID {} 의 Review 가 없습니다.", deliveredOrder.getId());
            }
        }

        return PageReviewPerStoreResponseDto.of(reviewDtoList, choiceStore);
    }

    /**
     * 특정 매장 메뉴별 총 판매 금액 조회
     *
     * @param pageNum
     * @param isDesc
     * @param storeId
     * @return
     */
    public PageTotalPricePerStoreResponseDto getEarning(
            AuthenticationUser authenticationUser, Boolean isDesc, Integer pageNum,
             Long storeId) {

        adminUserStatusCheck(authenticationUser);

        // 스토어를 찾는다.
        // 해당 스토어 아이디로 메뉴들을 찾는다.
        // 해당 메뉴 아이디로 주문 내역을 찾는다.
        // 주문 내역을 가져와 팔린 개수를 카운트한다.
        // page 안에 dto를 넣어 반환한다..?

        Store findStore = storeAdapter.queryStoreById(storeId);
        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);

        Page<Menu> menuPage = menuAdapter.queryMenuListByStoreId(findStore.getId(), pageable);
        PageUtil.validatePage(pageNum, menuPage);

        Long allMenuTotalEarning = 0L;

        Page<TotalPricePerStoreResponseDto> responsePage = menuPage.map(menu -> {
            Integer count = 0;
            List<OrderItem> orderItemList = orderItemRepository.findAllByMenu(menu);

            for (OrderItem item : orderItemList) {
                if (item.getOrder().getOrderStatus().equals(OrderStatus.DELIVERY_COMPLETED)) {
                    count += item.getQuantity();
                }
            }

            // 메뉴별 수익
            Long specificMenuSum = menu.getMenuPrice() * count;
            //모든 메뉴 수익(특정매장 수익)
            return TotalPricePerStoreResponseDto.of(menu, specificMenuSum);
        });

        for (TotalPricePerStoreResponseDto r : responsePage.getContent()) {
            allMenuTotalEarning += r.getMenuTotalPrice();
        }

        return PageTotalPricePerStoreResponseDto.of(pageNum, findStore, allMenuTotalEarning, responsePage);
    }

    //(ADMIN 권한의) 유저 Status 가 ENABLE 인지 확인
    private void adminUserStatusCheck(AuthenticationUser authenticationUser) {
        String email = authenticationUser.getUsername();
        User adminUser = userAdapter.queryUserByEmail(email);
        checkManagerEnable(adminUser);
    }

}
