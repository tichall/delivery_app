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

    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    public Menu checkValidMenuByIdAndMenuStatus(Long menuId) {
        Menu menu = findById(menuId);

        if(menu.getMenuStatus().equals(MenuStatus.DISABLE)) {
            throw new MenuNotFoundException(MenuErrorCode.DELETED_MENU);
        }

        return menu;
    }

    public void deleteMenu(Long menuId) {
        Menu menu = findById(menuId);
        menuRepository.delete(menu);
    }

    public Menu queryMenuById(Long menuId) {
        return findById(menuId);
    }

    private Menu findById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() ->
                new MenuNotFoundException(MenuErrorCode.MENU_NOT_FOUND));
    }

}
