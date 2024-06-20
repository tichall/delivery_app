package com.sparta.delivery_app.domain.menu.service;

import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.dto.request.MenuAddRequestDto;
import com.sparta.delivery_app.domain.menu.dto.request.MenuModifyRequestDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuAddResponseDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuModifyResponseDto;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuAdaptor menuAdaptor;
    private final StoreAdaptor storeAdaptor;

    public MenuAddResponseDto addMenu(MenuAddRequestDto requestDto) {

        Store store = storeAdaptor.getStore(requestDto.getStoreId());

        Menu menu = Menu.of(store, requestDto);
        menuAdaptor.saveMenu(menu);

        return MenuAddResponseDto.of(menu);
    }

    @Transactional
    public MenuModifyResponseDto modifyMenu(Long menuId, MenuModifyRequestDto requestDto) {

        Menu menu = menuAdaptor.checkValidMenuByIdAndMenuStatus(menuId);

        menu.updateMenu(requestDto);

        return new MenuModifyResponseDto(menu.getId(), menu.getMenuName(),
                menu.getMenuPrice(), menu.getMenuInfo(), menu.getMenuImagePath());
    }
}
