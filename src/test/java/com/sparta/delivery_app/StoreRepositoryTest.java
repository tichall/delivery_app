package com.sparta.delivery_app;

import com.sparta.delivery_app.config.TestConfig;
import com.sparta.delivery_app.domain.common.Page.PageSortBy;
import com.sparta.delivery_app.domain.common.Page.PageUtil;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import com.sparta.delivery_app.domain.store.repository.StoreSearchCond;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.sparta.delivery_app.domain.common.Page.PageConstants.PAGE_SIZE_FIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test") // application-"test".yml 파일로 적용
@Import(TestConfig.class)
public class StoreRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    User consumer;
    User tempConsumer;
    User manager;
    Store testStore;

    @BeforeEach
    void setInitData() {
        consumer = User.builder()
                .email("test1@gmail.com")
                .userAddress("주소")
                .nickName("b2consumer1")
                .name("고객님-스파르타")
                .userRole(UserRole.CONSUMER)
                .userStatus(UserStatus.ENABLE)
                .build();
        entityManager.persistAndFlush(consumer);

        tempConsumer = User.builder()
                .email("test2@gmail.com")
                .userAddress("주소")
                .nickName("b2consumer2")
                .name("고객님-스파르타2")
                .userRole(UserRole.CONSUMER)
                .userStatus(UserStatus.ENABLE)
                .build();
        entityManager.persistAndFlush(tempConsumer);

        manager = User.builder()
                .email("test2@gmail.com")
                .userAddress("주소")
                .nickName("b2")
                .name("사장님-스파르타")
                .userRole(UserRole.MANAGER)
                .userStatus(UserStatus.ENABLE)
                .build();
        entityManager.persistAndFlush(manager);

        Store testStore2 = Store.builder()
                .user(manager)
                .storeName("폐업 매장")
                .storeAddress("서울시 강남구")
                .storeRegistrationNumber("111-45-67890")
                .minTotalPrice(6000L)
                .storeInfo("정통 이태리 음식점입니다.")
                .status(StoreStatus.DISABLE)
                .build();
        entityManager.persistAndFlush(testStore2);

        StoreLiked storeLiked2 = StoreLiked.builder()
                .user(consumer)
                .store(testStore)
                .build();
        entityManager.persistAndFlush(storeLiked2);

        for (int i = 0; i < 20; i++) {
            manager = User.builder()
                    .email("test2@gmail.com" + i)
                    .userAddress("주소")
                    .nickName("b2" + i)
                    .name("사장님-스파르타" + i)
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            entityManager.persistAndFlush(manager);

            Store testStore = Store.builder()
                    .user(manager)
                    .storeName("이태리식 레스토랑" + i)
                    .storeAddress("서울시 강남구")
                    .storeRegistrationNumber("111-45-67890")
                    .minTotalPrice(5000L * (i + 1))
                    .storeInfo("정통 이태리 음식점입니다.")
                    .status(StoreStatus.ENABLE)
                    .build();
            entityManager.persistAndFlush(testStore);

            StoreLiked storeLiked = StoreLiked.builder()
                    .user(consumer)
                    .store(testStore)
                    .build();
            entityManager.persistAndFlush(storeLiked);
        }

        for (int i = 0; i < 5; i++) {
            manager = User.builder()
                    .email("test3@gmail.com" + i)
                    .userAddress("주소")
                    .nickName("b2" + i)
                    .name("사장님" + i)
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            entityManager.persistAndFlush(manager);

            Store testStore = Store.builder()
                    .user(manager)
                    .storeName("매장" + i)
                    .storeAddress("서울시 강남구")
                    .storeRegistrationNumber("111-45-67890")
                    .minTotalPrice(5000L * (i + 1))
                    .storeInfo("매장")
                    .status(StoreStatus.ENABLE)
                    .build();
            entityManager.persistAndFlush(testStore);

            StoreLiked tempStoreLiked1 = StoreLiked.builder()
                    .user(consumer)
                    .store(testStore)
                    .build();
            entityManager.persistAndFlush(tempStoreLiked1);

            StoreLiked tempStoreLiked2 = StoreLiked.builder()
                    .user(tempConsumer)
                    .store(testStore)
                    .build();
            entityManager.persistAndFlush(tempStoreLiked2);
        }
    }


    @Test
    public void 좋아요한_매장_목록_조회(){
        //given
        StoreSearchCond cond = StoreSearchCond.builder()
                .likedUserId(consumer.getId())
                .minTotalPriceLoe(null)
                .minTotalPriceGoe(null)
                .build();

        Pageable pageable = PageUtil.createPageable(1, PAGE_SIZE_FIVE, PageSortBy.SORT_BY_CREATED_AT.getSortBy(), true);

        //when
        Page<Store> storeList = storeRepository.searchLikedStore(cond, pageable);

        //then
        assertEquals(25, storeList.getTotalElements());
    }

    @Test
    public void 좋아요한_매장_목록_조회_최소주문금액_필터_15000원_이하(){
        //given
        StoreSearchCond cond = StoreSearchCond.builder()
                .likedUserId(consumer.getId())
                .minTotalPriceLoe(15000L)
                .minTotalPriceGoe(null)
                .build();

        Pageable pageable = PageUtil.createPageable(1, PAGE_SIZE_FIVE, PageSortBy.SORT_BY_CREATED_AT.getSortBy(), true);

        //when
        Page<Store> storeList = storeRepository.searchLikedStore(cond, pageable);

        //then
        assertEquals(6, storeList.getTotalElements());
    }

    @Test
    public void 좋아요한_매장_목록_조회_최소주문금액_필터_15000원_이상(){
        //given
        StoreSearchCond cond = StoreSearchCond.builder()
                .likedUserId(consumer.getId())
                .minTotalPriceLoe(null)
                .minTotalPriceGoe(15000L)
                .build();

        Pageable pageable = PageUtil.createPageable(1, PAGE_SIZE_FIVE, PageSortBy.SORT_BY_CREATED_AT.getSortBy(), true);

        //when
        Page<Store> storeList = storeRepository.searchLikedStore(cond, pageable);

        //then
        assertEquals(21, storeList.getTotalElements());
    }

    @Test
    public void 좋아요한_매장_수_조회(){
        //given & when
        Long totalLikedStore = storeRepository.countTotalLikedStore(consumer.getId());

        //then
        assertEquals(25, totalLikedStore);
    }

//    @Test
//    public void 좋아요_수_상위_10개_매장_조회(){
//
//        //when
//        List<Store> storeList = storeRepository.findTotalLikedTopTenStore();
//
//        //then
//        assertEquals(10L, storeList.size());
//        assertEquals();
//    }
}
