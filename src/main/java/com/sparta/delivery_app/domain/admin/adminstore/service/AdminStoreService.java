package com.sparta.delivery_app.domain.admin.adminstore.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.admin.adminstore.dto.*;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.order.adaptor.OrderAdaptor;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.order.repository.OrderItemRepository;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static com.sparta.delivery_app.domain.user.entity.UserStatus.checkManagerEnable;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStoreService {

    private final MenuAdaptor menuAdaptor;
    private final StoreAdaptor storeAdaptor;
    private final UserAdaptor userAdaptor;
    private final OrderAdaptor orderAdaptor;
    private final OrderItemRepository orderItemRepository;

    public PageMenuPerStoreResponseDto getMenuListPerStore(
            Long storeId, AuthenticationUser authenticationUser, final Integer pageNum,

            final Boolean isDesc) {
        log.info("getMenuListPerStore-service");
        //(ADMIN 권한의) 유저 Status 가 ENABLE 인지 확인
        adminUserStatusCheck(authenticationUser);

        Store choiceStore = storeAdaptor.queryStoreById(storeId);
        Pageable pageable = PageUtil.createPageable(pageNum,PageUtil.PAGE_SIZE_FIVE, isDesc);

        Page<Menu> menuPage = menuAdaptor.queryMenuListByStoreId(storeId, pageable);
        PageUtil.validatePage(pageNum, menuPage);

        return PageMenuPerStoreResponseDto.of(pageNum, choiceStore);
    }

    public PageReviewPerStoreResponseDto getReviewListPerStore(
            AuthenticationUser authenticationUser, Long storeId, final Integer pageNum,
            String sortBy, Boolean isDesc) {
        log.info("특정 매장 모든 리뷰 조회-service 시작");
        adminUserStatusCheck(authenticationUser);
        Store choiceStore = storeAdaptor.queryStoreById(storeId);

        //storeId 와 OrderStatus.DELIVERY_COMPLETED 로 orderList 가져와서 하나씩 responseDto 에 담기
        List<Order> deliveredOrderList = orderAdaptor.queryOrderListByStoreIdAndOrderStatus(storeId, OrderStatus.DELIVERY_COMPLETED);
        List<ReviewPerStoreResponseDto> reviewDtoList = new ArrayList<>();

        for (Order deliveredOrder : deliveredOrderList) {
            UserReviews userReview = deliveredOrder.getUserReviews();
            if(userReview != null) {
                ReviewPerStoreResponseDto ReviewResponseDto = ReviewPerStoreResponseDto.of(userReview);
                reviewDtoList.add(ReviewResponseDto);
            } else {
                log.warn("Order ID {} 의 Review 가 없습니다.", deliveredOrder.getId());
            }
        }

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);
        Page<OrderItem> orderItemPage = orderItemRepository.findAll(pageable);
        PageUtil.validatePage(pageNum, orderItemPage);

        return PageReviewPerStoreResponseDto.of(reviewDtoList, pageNum, choiceStore);
    }

    /**
     * 특정 매장 메뉴별 총 판매 금액 조회
     * @param pageNum
     * @param sortBy
     * @param isDesc
     * @param storeId
     * @return
     */
    public PageTotalPricePerStoreResponseDto getEarning(
            AuthenticationUser authenticationUser, Integer pageNum, String sortBy,
            Boolean isDesc, Long storeId) {

        adminUserStatusCheck(authenticationUser);

        Store findStore = storeAdaptor.queryStoreById(storeId);
        List<Menu> menuList = findStore.getMenuList();

        Map<Long, TotalPricePerStoreResponseDto> earningMap = new HashMap<>();

        for(Menu menu : menuList) {
            // 해당 매뉴의 판매수
            Integer count = 0;
            List<OrderItem> itemList = orderItemRepository.findAllByMenu(menu);

            for(OrderItem item : itemList) {
                if(item.getOrder().getOrderStatus().equals(OrderStatus.DELIVERY_COMPLETED)) {
                    count += item.getQuantity();
                }
            }

            Long sum = menu.getMenuPrice() * count;

            TotalPricePerStoreResponseDto responseDto = TotalPricePerStoreResponseDto.of(menu, sum);

            earningMap.put(menu.getId(), responseDto);
        }

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);

        Page<OrderItem> orderItemPage = orderItemRepository.findAll(pageable);

        PageUtil.validatePage(pageNum, orderItemPage);

        return PageTotalPricePerStoreResponseDto.of(pageNum, findStore, earningMap);
    }

    //(ADMIN 권한의) 유저 Status 가 ENABLE 인지 확인
    private void adminUserStatusCheck(AuthenticationUser authenticationUser) {
        String email = authenticationUser.getUsername();
        User adminUser = userAdaptor.queryUserByEmail(email);
        checkManagerEnable(adminUser);
    }

}
