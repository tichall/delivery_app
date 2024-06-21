package com.sparta.delivery_app.domain.openApi.service;


import com.sparta.delivery_app.domain.openApi.adapter.OpenApiAdapter;
import com.sparta.delivery_app.domain.openApi.dto.StoreDetailsResponseDto;
import com.sparta.delivery_app.domain.openApi.dto.StoreListReadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiAdapter openApiAdapter;

    /**
     * 전체 매장 조회
     * @param remoteAddr
     * @return
     */
    public List<StoreListReadResponseDto> findStores(String remoteAddr) {
        return openApiAdapter.queryStores();
    }

    /**
     * 특정 매장의 정보 조회
     * @param storeId
     * @param remoteAddr
     * @return
     */
    public StoreDetailsResponseDto findMenus(Long storeId, String remoteAddr) {
        return openApiAdapter.queryMenusByStoreId(storeId);
    }

}
