package com.sparta.delivery_app.domain.openApi.service;


import com.sparta.delivery_app.domain.menu.dto.response.MenuListReadResponseDto;
import com.sparta.delivery_app.domain.openApi.adapter.OpenApiAdapter;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.dto.response.StoreListReadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiAdapter openApiAdapter;
    private final StoreAdaptor storeAdaptor;

    /**
     * 전체 매장 조회
     * @param remoteAddr
     * @return
     */
    public List<StoreListReadResponseDto> findStores(String remoteAddr) {
        return openApiAdapter.queryStores();
    }
}
