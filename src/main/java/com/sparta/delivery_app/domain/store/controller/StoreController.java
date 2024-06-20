package com.sparta.delivery_app.domain.store.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.response.RegisterStoreResponseDto;
import com.sparta.delivery_app.domain.store.service.StoreService;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("")
    public ResponseEntity<RestApiResponse<RegisterStoreResponseDto>> registerStore(@Valid @RequestBody RegisterStoreRequestDto requestDto,
                                                                  User user) {
        RegisterStoreResponseDto responseDto = storeService.registerStore(requestDto, user);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("매장 등록이 완료되었습니다.", responseDto));

    }
}
