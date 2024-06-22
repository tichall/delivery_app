package com.sparta.delivery_app.domain.openApi.adapter;

import com.sparta.delivery_app.domain.openApi.dto.StoreDetailsResponseDto;
import com.sparta.delivery_app.domain.openApi.dto.StoreListReadResponseDto;
import com.sparta.delivery_app.domain.openApi.entity.OpenApi;
import com.sparta.delivery_app.domain.openApi.repository.OpenApiRepository;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenApiAdapter {

    private final StoreRepository storeRepository;
    private final StoreAdaptor storeAdaptor;
    private final OpenApiRepository openApiRepository;

    /**
     * ENABLE 상태인 매장만 조회
     * @return
     */
    public List<StoreListReadResponseDto> queryStores(String remoteAddr) {



        OpenApi openApi = new OpenApi(remoteAddr);
        openApiRepository.save(openApi);

        return storeRepository.findAll().stream()
                .filter(b -> b.getStatus().equals(StoreStatus.ENABLE))
                .map(StoreListReadResponseDto::new).toList();
    }

    /**
     * 특정 매장의 정보 조회
     * @param storeId
     * @return
     */
    public StoreDetailsResponseDto queryMenusByStoreId(Long storeId) {

        Store store = storeAdaptor.queryStoreById(storeId);
        StoreStatus.checkStoreStatus(store);

        return new StoreDetailsResponseDto(store);
    }

}
