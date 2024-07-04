package com.sparta.delivery_app.domain.admin.adminuser.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.admin.adminuser.dto.PageAdminUserResponseDto;
import com.sparta.delivery_app.domain.admin.adminuser.service.AdminUserService;
import com.sparta.delivery_app.domain.admin.adminuser.dto.AdminUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /*
     * 회원 목록 전체 조회
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping// 다른 페이지나 오름차순 조회하고 싶으면 /api/v1/admin/users?pageNum=2&isDesc=false 등오로 가능
    public ResponseEntity<RestApiResponse<PageAdminUserResponseDto>> allUserList(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") final String sortBy,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") Boolean isDesc
    ) {
        log.info("ADMIN-UserController");

        PageAdminUserResponseDto allUser = adminUserService.getAllUserList(authenticationUser, pageNum, sortBy, isDesc);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("조회 성공", allUser));
    }

}
