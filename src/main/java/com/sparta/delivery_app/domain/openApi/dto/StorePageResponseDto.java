package com.sparta.delivery_app.domain.openApi.dto;

import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class StorePageResponseDto {

    private Integer currentPage;
    private Long totalStore;
    private List<StoreListReadResponseDto> storeList;

    public static StorePageResponseDto of(Integer currentPage, Page<Store> storePage) {
        return StorePageResponseDto.builder()
                .currentPage(currentPage)
                .totalStore(storePage.getTotalElements())
                .storeList(storePage.getContent().stream()
                        .map(StoreListReadResponseDto::of).toList())
                .build();
    }

}
