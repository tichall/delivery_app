package com.sparta.delivery_app.domain.openApi.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.menu.dto.response.MenuListReadResponseDto;
import com.sparta.delivery_app.domain.openApi.service.OpenApiService;
import com.sparta.delivery_app.domain.store.dto.response.StoreListReadResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/v1")
public class OpenApiController {

    private final OpenApiService openApiService;

    /**
     * 전체 매장 조회
     * @param http
     * @return
     */
    @GetMapping("/stores")
    public ResponseEntity<RestApiResponse<List<StoreListReadResponseDto>>> storeList(
            HttpServletRequest http
    ) {
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("전체 매장 조회에 성공했습니다.", openApiService.findStores(http.getRemoteAddr())));
    }
}
