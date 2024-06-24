package com.sparta.delivery_app.domain.admin.adminstore.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.admin.adminstore.dto.PageMenuPerStoreResponseDto;
import com.sparta.delivery_app.domain.admin.adminstore.dto.ReviewPerStoreResponseDto;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.order.adaptor.OrderAdaptor;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
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

import java.util.ArrayList;
import java.util.List;

import static com.sparta.delivery_app.domain.user.entity.UserStatus.checkManagerEnable;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStoreService {

    private final MenuAdaptor menuAdaptor;
    private final StoreAdaptor storeAdaptor;
    private final UserAdaptor userAdaptor;
    private final OrderAdaptor orderAdaptor;

    public PageMenuPerStoreResponseDto getMenuListPerStore(
            Long storeId, AuthenticationUser authenticationUser, final Integer pageNum, final Boolean isDesc) {
        log.info("getMenuListPerStore-service");

        //(ADMIN 권한의) 유저 Status 가 ENABLE 인지 확인
        adminUserStatusCheck(authenticationUser);

        Store choiceStore = storeAdaptor.queryStoreById(storeId);
        Pageable pageable = PageUtil.createPageable(pageNum,PageUtil.PAGE_SIZE_FIVE, isDesc);

        Page<Menu> menuPage = menuAdaptor.queryMenuListByStoreId(storeId, pageable);
        PageUtil.validatePage(pageNum, menuPage);

        return PageMenuPerStoreResponseDto.of(pageNum, choiceStore);
    }

    //    public PageReviewPerStoreResponseDto getReviewListPerStore(
    public List<ReviewPerStoreResponseDto> getReviewListPerStore(
            AuthenticationUser authenticationUser, Long storeId, final Integer pageNum, final Boolean isDesc) {
        log.info("특정 매장 모든 리뷰 조회-service 시작");
        //(ADMIN 권한의) 유저 Status 가 ENABLE 인지 확인
        adminUserStatusCheck(authenticationUser);
//        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);

        //storeId 와 OrderStatus.DELIVERY_COMPLETED 로 orderList 가져와서 하나씩 responseDto 에 담기
        List<Order> deliveredOrderList = orderAdaptor.queryOrderListByStoreIdAndOrderStatus(storeId, OrderStatus.DELIVERY_COMPLETED);
        List<ReviewPerStoreResponseDto> reviewDtoList = new ArrayList<>();

        for (Order deliveredOrder : deliveredOrderList) {
            UserReviews userReview = deliveredOrder.getUserReviews();
            ReviewPerStoreResponseDto ReviewResponseDto = ReviewPerStoreResponseDto.of(storeId, userReview);
            reviewDtoList.add(ReviewResponseDto);
        }

        return reviewDtoList;
    }
    //페이징처리
//        PageReviewPerStoreResponseDto(reviewDtoList);
//
//        List<UserReviews> Reviews = orderList.stream().map(
//                order -> reviewAdaptor.queryReviewListByOrderId(order.getId());
//
//        PageUtil.validatePage(pageNum, ReviewPage);
//
//        return PageReviewPerStoreResponseDto.of(pageNum, choiceStore);
//    }

    //(ADMIN 권한의) 유저 Status 가 ENABLE 인지 확인
    private void adminUserStatusCheck(AuthenticationUser authenticationUser) {
        String email = authenticationUser.getUsername();
        User adminUser = userAdaptor.queryUserByEmail(email);
        checkManagerEnable(adminUser);
    }
}
