package com.sparta.delivery_app.domain.review.repository;

import static com.sparta.delivery_app.domain.review.entity.QUserReviews.userReviews;
import static com.sparta.delivery_app.domain.liked.entity.QReviewLiked.reviewLiked;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class UserReviewsRepositoryImpl implements UserReviewsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<UserReviews> searchLikedUserReviews(UserReviewsSearchCond cond, Pageable pageable) {
        JPAQuery<UserReviews> query = jpaQueryFactory.select(userReviews)
                .from(userReviews)
                .rightJoin(reviewLiked)
                .on(userReviews.eq(reviewLiked.userReviews))
                .where(
                        likedUserIdEq(cond.getLikedUserId()),
                        checkIsLiked(),
                        checkUserReviewsStatus()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()){
            PathBuilder pathBuilder = new PathBuilder(userReviews.getType(), userReviews.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<UserReviews> userReviewsList = query.fetch();

        Long totalElements = countTotalLikedUserReviews(cond.getLikedUserId());

        return PageableExecutionUtils.getPage(userReviewsList, pageable, () -> totalElements);
    }

    @Override
    public Long countTotalLikedUserReviews(Long userId) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(userReviews)
                .rightJoin(reviewLiked)
                .on(userReviews.eq(reviewLiked.userReviews))
                .where(
                        likedUserIdEq(userId),
                        checkIsLiked(),
                        checkUserReviewsStatus()
                )
                .fetchOne();
    }

    private BooleanExpression likedUserIdEq(Long likedUserId) {
        return Objects.nonNull(likedUserId) ? reviewLiked.user.id.eq(likedUserId) : null;
    }

    private BooleanExpression checkIsLiked() {
        return reviewLiked.isLiked.eq(true);
    }

    private BooleanExpression checkUserReviewsStatus() {
        return userReviews.reviewStatus.eq(ReviewStatus.ENABLE);
    }
}
