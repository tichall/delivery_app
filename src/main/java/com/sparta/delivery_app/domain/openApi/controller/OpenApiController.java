package com.sparta.delivery_app.domain.openApi.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.openApi.dto.StoreDetailsResponseDto;
import com.sparta.delivery_app.domain.openApi.service.OpenApiService;
import com.sparta.delivery_app.domain.openApi.dto.StoreListReadResponseDto;
import io.github.bucket4j.Bucket;
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
    private final Bucket bucket;

    /**
     * 전체 매장 조회
     * @param http
     * @return
     */
    @GetMapping("/stores")
    public ResponseEntity<RestApiResponse<List<StoreListReadResponseDto>>> storeList(
            HttpServletRequest http
    ) {

        if(!bucket.tryConsume(1)) {
            throw new IllegalArgumentException("비정상적인 접근입니다!");
        }

        List<StoreListReadResponseDto> storeList = openApiService.findStores(http.getRemoteAddr());

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("전체 매장 조회에 성공했습니다.", storeList));
    }

    /**
     * 특정 매장의 정보 조회
     * @param storeId
     * @param http
     * @return
     */
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<RestApiResponse<StoreDetailsResponseDto>> menuList(
            @PathVariable Long storeId,
            HttpServletRequest http
    ) {
        StoreDetailsResponseDto storeDetails = openApiService.findMenus(storeId, http.getRemoteAddr());

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("선택하신 매장의 정보 조회에 성공 했습니다.", storeDetails));
    }

}
