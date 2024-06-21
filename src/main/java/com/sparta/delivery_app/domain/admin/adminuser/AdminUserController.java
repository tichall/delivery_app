package com.sparta.delivery_app.domain.admin.adminuser;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    //회원 목록 전체 조회
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ///페이지 바꾸고 싶을 땐 api/admin/users?page=6&size=20 등으로 접속
    public ResponseEntity<RestApiResponse<Page<AdminUserResponseDto>>> allUserList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value ="size", defaultValue = "10") int size) {
        Page<AdminUserResponseDto> allUserList = adminUserService.getAllUserList(page-1, size);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("조회 성공", allUserList));
    }

}
