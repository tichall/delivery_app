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

    /**
     * 메뉴 등록
     * @param requestDto
     * @return responseDto
     */
    public MenuAddResponseDto addMenu(final MenuAddRequestDto requestDto) {

        Store store = storeAdaptor.queryStoreById(requestDto.storeId());

        Menu menu = Menu.of(store, requestDto);
        menuAdaptor.saveMenu(menu);

        return MenuAddResponseDto.of(menu);
    }

    /**
     * 메뉴 수정
     * @param menuId
     * @param requestDto
     * @return responseDto
     */
    @Transactional
    public MenuModifyResponseDto modifyMenu(Long menuId, final MenuModifyRequestDto requestDto) {

        Menu menu = menuAdaptor.queryMenuByIdAndMenuStatus(menuId);
        menu.updateMenu(requestDto);

        return MenuModifyResponseDto.of(menu);
    }

    /**
     * 메뉴 삭제
     * @param menuId
     */
    @Transactional
    public void deleteMenu(Long menuId) {
        Menu menu = menuAdaptor.queryMenuByIdAndMenuStatus(menuId);
        menu.deleteMenu();
    }
}
