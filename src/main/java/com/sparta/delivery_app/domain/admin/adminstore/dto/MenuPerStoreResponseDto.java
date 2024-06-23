package com.sparta.delivery_app.domain.admin.adminstore.dto;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuPerStoreResponseDto {

    private Long menuId;
    private String menuName;
    private Long menuPrice;
    private MenuStatus menuStatus;
    private Long totalOrderCountPerMenu;

    public static MenuPerStoreResponseDto of(Menu menu, Long totalOrderCountPerMenu) {
        return MenuPerStoreResponseDto.builder()
                .menuId(menu.getId())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .menuStatus(menu.getMenuStatus())
                .totalOrderCountPerMenu(totalOrderCountPerMenu)
                .build();
    }
}
