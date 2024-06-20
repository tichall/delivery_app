package com.sparta.delivery_app.domain.menu.controller;

import com.sparta.delivery_app.domain.menu.dto.request.MenuAddRequestDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuAddResponseDto;
import com.sparta.delivery_app.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MenuController {

    private final MenuService menuService;

    /**
     * 메뉴 등록
     */
    @PostMapping("/menus")
    public MenuAddResponseDto menuAdd(
            @RequestBody MenuAddRequestDto requestDto
    ) {
        return menuService.addMenu(requestDto);
    }

}
