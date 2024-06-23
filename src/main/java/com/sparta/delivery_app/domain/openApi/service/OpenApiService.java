package com.sparta.delivery_app.domain.openApi.service;


import com.sparta.delivery_app.common.exception.errorcode.PageErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.OpenApiAccessDeniedException;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.openApi.adapter.OpenApiAdapter;
import com.sparta.delivery_app.domain.openApi.dto.StoreDetailsResponseDto;
import com.sparta.delivery_app.domain.openApi.dto.StoreListReadResponseDto;
import com.sparta.delivery_app.domain.openApi.dto.StorePageResponseDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiAdapter openApiAdapter;
    private final Bucket bucket;

    /**
     * 전체 매장 조회
     * @return
     */
    public StorePageResponseDto findStores(Integer pageNum, String sortBy, Boolean isDesc) {

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, sortBy, isDesc);

        Page<Store> storePage = openApiAdapter.queryStores(pageable);

        PageUtil.validatePage(pageNum, storePage);

        return StorePageResponseDto.of(pageNum, storePage);
    }

    /**
     * 특정 매장의 정보 조회
     * @param storeId
     * @return
     */
    public StoreDetailsResponseDto findMenus(Long storeId) {
        return openApiAdapter.queryMenusByStoreId(storeId);
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
