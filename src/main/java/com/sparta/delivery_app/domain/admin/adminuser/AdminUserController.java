package com.sparta.delivery_app.domain.admin.adminuser;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    //회원 목록 전체 조회 //{email(이메일), name(성함), nickName(닉네임), address(전체 주소), status(회원 상태)},..
    @GetMapping
    public ResponseEntity<RestApiResponse<List<AdminUserResponseDto>>> allUserList() {
        List<AdminUserResponseDto> allUserList = adminUserService.getAllUserList();
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("조회 성공.", allUserList));
    }

}
