package com.sparta.delivery_app;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.config.TestConfig;
import com.sparta.delivery_app.domain.common.Page.PageConstants;
import com.sparta.delivery_app.domain.common.Page.PageUtil;
import com.sparta.delivery_app.domain.liked.entity.ReviewLiked;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.UserReviewsRepository;
import com.sparta.delivery_app.domain.review.repository.UserReviewsSearchCond;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
public class UserReviewsRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserReviewsRepository userReviewsRepository;

    User testUser;
    User testUser2;

    @BeforeEach
    void setInitData() {
        testUser = User.builder()
                    .email("test1@gmail.com")
                    .userAddress("고객님 여기는 어디야")
                    .nickName("b2consumer1")
                    .name("고객님-스파르타")
                    .userRole(UserRole.CONSUMER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
        entityManager.persist(testUser);

        testUser2 = User.builder()
                .email("test2@gmail.com")
                .userAddress("고객님 여기는 어디야")
                .nickName("b2consumer2")
                .name("고객님-스파르타")
                .userRole(UserRole.CONSUMER)
                .userStatus(UserStatus.ENABLE)
                .build();
        entityManager.persist(testUser2);

        Store testStore = Store.builder()
            .user(testUser)
            .storeName("이태리식 레스토랑")
            .storeAddress("서울시 강남구")
            .storeRegistrationNumber("111-45-67890")
            .minTotalPrice(5000L)
            .storeInfo("정통 이태리 음식점입니다.")
            .status(StoreStatus.ENABLE)
            .build();
        entityManager.persist(testStore);

        Menu menu = Menu.builder()
                    .store(testStore)
                    .menuName("매장 음식")
                    .menuPrice(10_000L)
                    .menuInfo("맛있다!")
                    .menuImagePath("image1/abc")
                    .menuStatus(MenuStatus.ENABLE)
                    .build();
        entityManager.persist(menu);

        for (int i = 0; i < 10; i++) {
            Order tempOrder = Order.builder()
                    .user(testUser)
                    .store(testStore)
                    .orderStatus(OrderStatus.DELIVERY_COMPLETED)
                    .build();
            entityManager.persist(tempOrder);

            OrderItem tempOrderItem1 = OrderItem.builder()
                    .order(tempOrder)
                    .menu(menu)
                    .quantity(5)
                    .build();

            tempOrder.addOrderItem(tempOrderItem1);

            UserReviews userReviews = UserReviews.builder()
                    .content("맛집입니다!! 강추" + i)
                    .reviewImagePath("path")
                    .rating(5)
                    .order(tempOrder)
                    .user(testUser)
                    .reviewStatus(ReviewStatus.ENABLE)
                    .build();

            entityManager.persist(userReviews);

            ReviewLiked testLiked = ReviewLiked.builder()
                    .userReviews(userReviews)
                    .user(testUser2)
                    .build();
            entityManager.persist(testLiked);
        }
    }

    @Test
    void 좋아요한_리뷰_조회하기() {
        // given
        Pageable pageable = PageUtil.createPageable(1, PageConstants.PAGE_SIZE_FIVE, true);
        UserReviewsSearchCond cond = UserReviewsSearchCond.builder()
                .likedUserId(testUser2.getId())
                        .build();

        // when
        Page<UserReviews> userReviewsPage = userReviewsRepository.searchLikedUserReviews(cond, pageable);

        // then
        assertEquals(10, userReviewsPage.getTotalElements());
    }

}
