package com.sparta.delivery_app.domain.menu.service;

import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.dto.request.MenuAddRequestDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuAddResponseDto;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuAdaptor menuAdaptor;

    public MenuAddResponseDto addMenu(MenuAddRequestDto requestDto) {

        Menu menu = new Menu(requestDto.getStore(), requestDto.getMenuName(),
                requestDto.getMenuPrice(), requestDto.getMenuInfo(),
                requestDto.getMenuImagePath(), MenuStatus.ENABLE);

        menuAdaptor.saveMenu(menu);

        return MenuAddResponseDto.of(menu);
    }
}
