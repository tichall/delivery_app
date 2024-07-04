package com.sparta.delivery_app.domain.liked.dto;

import com.sparta.delivery_app.domain.openApi.dto.StoreListReadResponseDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class LikedStorePageResponseDto {

    private Integer currentPage;
    private String totalStore;
    private List<StoreListReadResponseDto> storeList;

    public static LikedStorePageResponseDto of(Integer currentPage, String totalStore, Page<Store> storePage) {
        return LikedStorePageResponseDto.builder()
                .currentPage(currentPage)
                .totalStore(totalStore)
                .storeList(storePage.getContent().stream()
                        .map(StoreListReadResponseDto::of).toList())
                .build();
    }

}