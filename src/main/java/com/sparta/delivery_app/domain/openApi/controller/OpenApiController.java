package com.sparta.delivery_app.domain.openApi.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.openApi.dto.StoreDetailsResponseDto;
import com.sparta.delivery_app.domain.openApi.dto.StorePageResponseDto;
import com.sparta.delivery_app.domain.openApi.service.OpenApiService;
import com.sparta.delivery_app.domain.openApi.dto.StoreListReadResponseDto;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/v1")
public class OpenApiController {

    private final OpenApiService openApiService;

    /**
     * 전체 매장 조회
     * @return
     */
    @GetMapping("/stores")
    public ResponseEntity<RestApiResponse<StorePageResponseDto>> storeList(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") Boolean isDesc
    ) {

        openApiService.useToken();

        StorePageResponseDto responseDto = openApiService.findStores(pageNum, sortBy, isDesc);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("전체 매장 조회에 성공했습니다.", responseDto));
    }

    /**
     * 특정 매장의 정보 조회
     * @param storeId
     * @return
     */
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<RestApiResponse<StoreDetailsResponseDto>> menuList(
            @PathVariable Long storeId
    ) {
        openApiService.useToken();

        StoreDetailsResponseDto storeDetails = openApiService.findMenus(storeId);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("선택하신 매장의 정보 조회에 성공 했습니다.", storeDetails));
    }

    @GetMapping("/reviews")
    public ResponseEntity<RestApiResponse<String>> reviewList(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") Boolean isDesc
    ) {
        return null;
    }


}
