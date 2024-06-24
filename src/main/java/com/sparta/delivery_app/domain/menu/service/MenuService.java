package com.sparta.delivery_app.domain.menu.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.menu.adapter.MenuAdapter;
import com.sparta.delivery_app.domain.menu.dto.request.MenuAddRequestDto;
import com.sparta.delivery_app.domain.menu.dto.request.MenuModifyRequestDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuAddResponseDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuModifyResponseDto;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuAdapter menuAdapter;
    private final StoreAdapter storeAdapter;
    private final UserAdapter userAdapter;

    /**
     * 메뉴 등록
     * @param requestDto
     * @return responseDto
     */
    public MenuAddResponseDto addMenu(final MenuAddRequestDto requestDto, AuthenticationUser user) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        Store findStore = storeAdapter.queryStoreId(findUser);

        Menu menu = Menu.saveMenu(findStore, requestDto);
        menuAdapter.saveMenu(menu);

        return MenuAddResponseDto.of(menu);
    }

    /**
     * 메뉴 수정
     * @param menuId
     * @param requestDto
     * @param user
     * @return responseDto
     */
    @Transactional
    public MenuModifyResponseDto modifyMenu(final Long menuId, final MenuModifyRequestDto requestDto, AuthenticationUser user) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        Store store = storeAdapter.queryStoreId(findUser);
        Menu menu = menuAdapter.queryMenuByIdAndMenuStatus(menuId);

        menu.checkStoreMenuMatch(menu, store.getId());
        Menu updateMenu = menu.updateMenu(requestDto);

        return MenuModifyResponseDto.of(updateMenu);
    }

    /**
     * 메뉴 삭제
     * @param menuId
     * @param user
     */
    @Transactional
    public void deleteMenu(final Long menuId, AuthenticationUser user) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        Store store = storeAdapter.queryStoreId(findUser);
        Menu menu = menuAdapter.queryMenuByIdAndMenuStatus(menuId);

        menu.checkStoreMenuMatch(menu, store.getId());
        menu.deleteMenu();
    }
}