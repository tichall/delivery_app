package com.sparta.delivery_app.domain.menu.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.CommonErrorCode;
import com.sparta.delivery_app.common.exception.errorcode.MenuErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.MenuNotFoundException;
import com.sparta.delivery_app.domain.menu.entity.Menu;
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

    public Menu queryMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() ->
                new MenuNotFoundException(MenuErrorCode.MENU_NOT_FOUND));
    }
}
