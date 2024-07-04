package com.sparta.delivery_app.domain.openApi.service;


import com.sparta.delivery_app.common.exception.errorcode.PageErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.OpenApiAccessDeniedException;
import com.sparta.delivery_app.domain.common.Page.PageUtil;
import com.sparta.delivery_app.domain.openApi.adapter.OpenApiAdapter;
import com.sparta.delivery_app.domain.openApi.dto.ReviewPageResponseDto;
import com.sparta.delivery_app.domain.openApi.dto.StoreDetailsResponseDto;
import com.sparta.delivery_app.domain.openApi.dto.StorePageResponseDto;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.entity.Store;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.sparta.delivery_app.domain.common.Page.PageConstants.PAGE_SIZE_FIVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiAdapter openApiAdapter;
    private final Bucket bucket;

    /**
     * 전체 매장 조회
     * @param pageNum
     * @param isDesc
     * @return
     */
    public StorePageResponseDto findStores(final Integer pageNum, final String sortBy, final Boolean isDesc) {

        Pageable pageable = PageUtil.createPageable(pageNum, PAGE_SIZE_FIVE, sortBy, isDesc);

        Page<Store> storePage = openApiAdapter.queryStores(pageable);

        String totalStore = PageUtil.validateAndSummarizePage(pageNum, storePage);

        return StorePageResponseDto.of(pageNum, totalStore, storePage);
    }

    /**
     * 특정 매장의 정보 조회
     * @param storeId
     * @return
     */
    public StoreDetailsResponseDto findMenus(final Long storeId) {

        return openApiAdapter.queryMenusByStoreId(storeId);
    }

    /**
     * 전체 리뷰 조회
     * @param pageNum
     * @param isDesc
     * @return
     */
    public ReviewPageResponseDto findReviews(final Integer pageNum, final String sortBy, final Boolean isDesc) {
        Pageable pageable = PageUtil.createPageable(pageNum, PAGE_SIZE_FIVE, sortBy, isDesc);

        Page<UserReviews> reviewPage = openApiAdapter.queryReviews(pageable);

        String totalReview = PageUtil.validateAndSummarizePage(pageNum, reviewPage);

        return ReviewPageResponseDto.of(pageNum, totalReview, reviewPage);
    }

    /**
     * 토큰 사용
     */
    public void useToken() {
        if(!bucket.tryConsume(1)) {
            throw new OpenApiAccessDeniedException(PageErrorCode.UNABLE_TO_CONNECT);
        }
    }

}
