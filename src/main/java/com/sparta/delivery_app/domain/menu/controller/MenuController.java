package com.sparta.delivery_app.domain.menu.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.menu.dto.request.MenuAddRequestDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuAddResponseDto;
import com.sparta.delivery_app.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    /**
     * 메뉴 등록
     */
    @PostMapping
    public ResponseEntity<RestApiResponse<MenuAddResponseDto>> menuAdd(
            @Valid @RequestBody MenuAddRequestDto requestDto
    ) {
        MenuAddResponseDto responseDto =  menuService.addMenu(requestDto);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("메뉴 등록에 성공 했습니다.", responseDto));
    }

}
