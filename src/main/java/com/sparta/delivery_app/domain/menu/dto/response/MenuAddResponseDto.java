package com.sparta.delivery_app.domain.menu.dto.response;


import com.sparta.delivery_app.domain.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuAddResponseDto {

    private Long storeId;
    private String menuName;
    private Long menuPrice;
    private String menuInfo;
    private String menuImagePath;

    public static MenuAddResponseDto of(Menu menu) {
        return MenuAddResponseDto.builder()
                .storeId(menu.getStore().getId())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .menuInfo(menu.getMenuInfo())
                .menuImagePath(menu.getMenuImagePath())
                .build();
    }
}
