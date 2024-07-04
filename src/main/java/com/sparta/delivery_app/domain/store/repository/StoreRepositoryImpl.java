package com.sparta.delivery_app.domain.store.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.sparta.delivery_app.domain.store.entity.QStore.store;
import static com.sparta.delivery_app.domain.liked.entity.QStoreLiked.storeLiked;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Store> searchLikedStore(StoreSearchCond cond, Pageable pageable) {
        JPAQuery<Store> query = jpaQueryFactory.selectFrom(store)
                .rightJoin(storeLiked)
                .on(store.eq(storeLiked.store))
                .where(
                        likedUserIdEq(cond.getLikedUserId()),
                        checkIsLiked(),
                        checkStoreStatus()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()){
            PathBuilder pathBuilder = new PathBuilder(store.getType(), store.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<Store> storeList = query.fetch();

        Long totalElements = countTotalLikedStore(cond.getLikedUserId());

        return PageableExecutionUtils.getPage(storeList, pageable, () -> totalElements);
    }

    @Override
    public Long countTotalLikedStore(Long userId) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(store)
                .rightJoin(storeLiked)
                .on(store.eq(storeLiked.store))
                .where(
                        likedUserIdEq(userId),
                        checkIsLiked(),
                        checkStoreStatus()
                )
                .fetchOne();
    }

    private BooleanExpression likedUserIdEq(Long likedUserId) {
        return Objects.nonNull(likedUserId) ? storeLiked.user.id.eq(likedUserId) : null;
    }

    private BooleanExpression checkIsLiked(){
        return storeLiked.isLiked.eq(true);
    }

    private BooleanExpression checkStoreStatus() {
        return store.status.eq(StoreStatus.ENABLE);
    }

}
