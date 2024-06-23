package com.sparta.delivery_app.domain.menu.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.menu.dto.request.MenuAddRequestDto;
import com.sparta.delivery_app.domain.menu.dto.request.MenuModifyRequestDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuAddResponseDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuModifyResponseDto;
import com.sparta.delivery_app.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    /**
     * 메뉴 등록
     * @param requestDto
     * @param user
     * @return 성공 메시지 및 등록된 메뉴 정보
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<RestApiResponse<MenuAddResponseDto>> menuAdd(
            @Valid @RequestBody final MenuAddRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser user
            ) {
        final MenuAddResponseDto responseDto =  menuService.addMenu(requestDto, user);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("메뉴 등록에 성공 했습니다.", responseDto));
    }

    /**
     * 메뉴 수정
     * @param menuId
     * @param requestDto
     * @param user
     * @return 성공 메시지 및 수정된 메뉴 정보
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{menuId}")
    public ResponseEntity<RestApiResponse<MenuModifyResponseDto>> menuModify(
            @PathVariable Long menuId,
            @Valid @RequestBody final MenuModifyRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser user
    ) {
        final MenuModifyResponseDto responseDto = menuService.modifyMenu(menuId, requestDto, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("메뉴 수정에 성공 했습니다.", responseDto));
    }

    /**
     * 메뉴 삭제
     * @param menuId
     * @param user
     */
    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{menuId}")
    public ResponseEntity<RestApiResponse<Void>> menuDelete(
            @PathVariable Long menuId,
            @AuthenticationPrincipal AuthenticationUser user
    ) {
        menuService.deleteMenu(menuId, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("메뉴 삭제가 완료되었습니다."));
    }
}
