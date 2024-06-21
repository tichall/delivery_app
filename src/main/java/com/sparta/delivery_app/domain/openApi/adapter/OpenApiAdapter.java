package com.sparta.delivery_app.domain.openApi.adapter;

import com.sparta.delivery_app.domain.menu.dto.response.MenuListReadResponseDto;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.menu.repository.MenuRepository;
import com.sparta.delivery_app.domain.store.dto.response.StoreListReadResponseDto;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenApiAdapter {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    /**
     * ENABLE 상태인 매장만 조회
     * @return
     */
    public List<StoreListReadResponseDto> queryStores() {
        return storeRepository.findAll().stream()
                .filter(b -> b.getStatus().equals(StoreStatus.ENABLE))
                .map(StoreListReadResponseDto::new).toList();
    }
}
