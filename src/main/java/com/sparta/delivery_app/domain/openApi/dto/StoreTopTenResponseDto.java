package com.sparta.delivery_app.domain.openApi.dto;

import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreTopTenResponseDto {
    List<StoreListReadResponseDto> storeDtoList;

    public static StoreTopTenResponseDto of(List<Store> storeList) {
        return StoreTopTenResponseDto.builder()
                .storeDtoList(storeList.stream().map(StoreListReadResponseDto::of).toList())
                .build();
    }
}
