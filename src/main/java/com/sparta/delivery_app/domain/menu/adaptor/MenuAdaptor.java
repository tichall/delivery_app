package com.sparta.delivery_app.domain.menu.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.MenuErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.MenuNotFoundException;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuAdaptor {

    private final MenuRepository menuRepository;

    /**
     * 메뉴 등록
     * @param menu
     */
    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    /**
     * 메뉴 id, 상태 검증
     * @param menuId
     * @return menu
     */
    public Menu queryMenuByIdAndMenuStatus(Long menuId) {
        Menu menu = findById(menuId);
        MenuStatus.checkMenuStatus(menu);
        return menu;
    }

    /**
     * 메뉴 id 검증
     * @param menuId
     * @return menu
     */
    public Menu queryMenuById(Long menuId) {
        return findById(menuId);
    }

    /**
     * 메뉴 id 검증
     * @param menuId
     * @return menu
     */
    private Menu findById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() ->
                new MenuNotFoundException(MenuErrorCode.MENU_NOT_FOUND));
    }

}
