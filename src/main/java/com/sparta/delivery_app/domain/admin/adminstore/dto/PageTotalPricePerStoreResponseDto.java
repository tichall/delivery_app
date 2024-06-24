package com.sparta.delivery_app.domain.admin.adminstore.dto;


import com.sparta.delivery_app.domain.admin.adminstore.dto.TotalPricePerStoreResponseDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class PageTotalPricePerStoreResponseDto {

    /**
     * 페이징 처리 responseDto
     */
    private Integer currentPage;
    private int totalMenu;
    private Long storeId;
    private String storeName;
    private Long specificStoreEarning;
    private Map<Long, TotalPricePerStoreResponseDto> earningMap;

    public static PageTotalPricePerStoreResponseDto of(
            Integer currentPage, Store store, Long specificStoreEarning,
            Map<Long, TotalPricePerStoreResponseDto> earningMap) {

        return PageTotalPricePerStoreResponseDto.builder()
                .currentPage(currentPage)
                .totalMenu(earningMap.size())
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .earningMap(earningMap)
                .specificStoreEarning(specificStoreEarning)
                .build();

    }
}
