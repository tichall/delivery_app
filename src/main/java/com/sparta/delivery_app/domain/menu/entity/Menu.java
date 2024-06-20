package com.sparta.delivery_app.domain.menu.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.menu.dto.request.MenuAddRequestDto;
import com.sparta.delivery_app.domain.menu.dto.request.MenuModifyRequestDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuAddResponseDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuModifyResponseDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu")
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String menuName;

    @Column(nullable = false)
    private Long menuPrice;

    @Column(nullable = false)
    private String menuInfo;

    @Column
    private String menuImagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus menuStatus;

    @Builder
    public Menu(String menuName, Long menuPrice, String menuInfo, String menuImagePath, Store store, MenuStatus menuStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuInfo = menuInfo;
        this.menuImagePath = menuImagePath;
        this.store = store;
        this.menuStatus = menuStatus;
    }

    @Builder
    public static Menu of(Store store, MenuAddRequestDto requestDto) {
        return Menu.builder()
                .store(store)
                .menuName(requestDto.getMenuName())
                .menuPrice(requestDto.getMenuPrice())
                .menuInfo(requestDto.getMenuInfo())
                .menuImagePath(requestDto.getMenuImagePath())
                .menuStatus(MenuStatus.ENABLE)
                .build();
    }

//    @Builder
//    public static Menu updateMenu(MenuModifyRequestDto requestDto) {
//        return Menu.builder()
//                .menuName(requestDto.getMenuName())
//                .menuPrice(requestDto.getMenuPrice())
//                .menuInfo(requestDto.getMenuInfo())
//                .menuImagePath(requestDto.getMenuImagePath())
//                .menuStatus(MenuStatus.ENABLE)
//                .build();
//    }

    public void updateMenu(MenuModifyRequestDto requestDto) {
        this.menuName = requestDto.getMenuName();
        this.menuPrice = requestDto.getMenuPrice();
        this.menuInfo = requestDto.getMenuInfo();
        this.menuImagePath = requestDto.getMenuImagePath();
    }
}
