package com.sparta.delivery_app.domain.user.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.user.dto.request.ConsumersSignupRequestDto;
import com.sparta.delivery_app.domain.user.dto.response.ConsumersSignupResponseDto;
import com.sparta.delivery_app.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Manager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/consumers")
    public ResponseEntity<RestApiResponse<ConsumersSignupResponseDto>> ConsumersSignupRequestDto(@Valid @RequestBody ConsumersSignupRequestDto requestDto) {
        ConsumersSignupResponseDto responseDto = userService.ConsumersUserAdd(requestDto);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of(
                        "회원가입에 성공했습니다.",
                        responseDto)
                );
    }

    @PostMapping("/managers")
    public ResponseEntity<RestApiResponse<ConsumersSignupResponseDto>> managersSignupRequestDto(@Valid @RequestBody ConsumersSignupRequestDto requestDto) {
        ConsumersSignupResponseDto responseDto = userService.managersUserAdd(requestDto);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of(
                        "회원가입에 성공했습니다. 사장님, 우리 함께 성장해요!",
                        responseDto)
                );
    }

}