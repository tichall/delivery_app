package com.sparta.delivery_app.domain.admin.adminstore.dto;


import com.sparta.delivery_app.domain.admin.adminstore.dto.TotalPricePerStoreResponseDto;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class PageTotalPricePerStoreResponseDto {

    /**
     * 페이징 처리 responseDto
     */
    private Integer currentPage;
    private String totalMenu;
    private Long storeId;
    private String storeName;
    private Long specificStoreEarning;
    private List<TotalPricePerStoreResponseDto> earningMap;

    public static PageTotalPricePerStoreResponseDto of(
            Integer currentPage, String totalMenu, Store store, Long specificStoreEarning, Page<TotalPricePerStoreResponseDto> menuPage) {

        return PageTotalPricePerStoreResponseDto.builder()
                .currentPage(currentPage)
                .totalMenu(totalMenu)
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .specificStoreEarning(specificStoreEarning)
                .earningMap(menuPage.stream().toList())
                .build();

    }
}
