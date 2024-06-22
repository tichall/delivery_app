package com.sparta.delivery_app.domain.user.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.user.dto.request.ConsumersSignupRequestDto;
import com.sparta.delivery_app.domain.user.dto.request.UserModifyRequestDto;
import com.sparta.delivery_app.domain.user.dto.request.UserResignRequestDto;
import com.sparta.delivery_app.domain.user.dto.response.ConsumersSignupResponseDto;
import com.sparta.delivery_app.domain.user.dto.response.UserModifyResponseDto.UserModifyResponseDto;
import com.sparta.delivery_app.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/consumers")
    public ResponseEntity<RestApiResponse<ConsumersSignupResponseDto>> ConsumersSignupRequestDto(
            @Valid @RequestBody ConsumersSignupRequestDto requestDto) {
        ConsumersSignupResponseDto responseDto = userService.consumersUserAdd(requestDto);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of(
                        "회원가입에 성공했습니다.",
                        responseDto)
                );
    }

    @PostMapping("/managers")
    public ResponseEntity<RestApiResponse<ConsumersSignupResponseDto>> managersSignupRequestDto(
            @Valid @RequestBody ConsumersSignupRequestDto requestDto) {
        ConsumersSignupResponseDto responseDto = userService.managersUserAdd(requestDto);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of(
                        "회원가입에 성공했습니다. 사장님, 우리 함께 성장해요!",
                        responseDto)
                );
    }

    @DeleteMapping("/resign")
    public ResponseEntity<RestApiResponse<String>> resign(
            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody UserResignRequestDto userResignRequestDto
    ) {
        userService.resignUser(user, userResignRequestDto);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("탈퇴 되었습니다."));
    }

    @PutMapping("/profile")
    public ResponseEntity<RestApiResponse<?>> profileModify(
            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody UserModifyRequestDto requestDto
    ) {
        UserModifyResponseDto responseDto = userService.modifyProfile(requestDto, user);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of(responseDto));
    }

}