package com.sparta.delivery_app.domain.admin.adminstore.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.admin.adminstore.dto.PageMenuPerStoreResponseDto;
import com.sparta.delivery_app.domain.admin.adminstore.dto.PageTotalPricePerStoreResponseDto;
import com.sparta.delivery_app.domain.admin.adminstore.dto.ReviewPerStoreResponseDto;
import com.sparta.delivery_app.domain.admin.adminstore.service.AdminStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/stores")
public class AdminStoreController {

    private final AdminStoreService adminStoreService;

    /*
     * 매장별 메뉴 및 리뷰 다건 조회
     */

    // 1. 각 매장에서 판매하고 있는 메뉴를 조회 (삭제 메뉴 포함)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{storeId}/menus")
    public ResponseEntity<RestApiResponse<PageMenuPerStoreResponseDto>> getMenuListPerStore
    (@PathVariable Long storeId,
     @AuthenticationPrincipal AuthenticationUser authenticationUser,
     @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
     @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc) {
        log.info("특정매장 모든메뉴 조회-controller");

        PageMenuPerStoreResponseDto responseDto = adminStoreService.getMenuListPerStore(storeId,
                authenticationUser, pageNum, isDesc);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("조회 성공", responseDto));
    }


    //매장별 리뷰 다건 조회(폐업 매장과 삭제 리뷰 포함)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{storeId}/reviews")
//    public ResponseEntity<RestApiResponseM<PageReviewPerStoreResponseDto>> getReviewListPerStore
    public ResponseEntity<RestApiResponse<List<ReviewPerStoreResponseDto>>> getReviewListPerStore
    (@PathVariable Long storeId,
     @AuthenticationPrincipal AuthenticationUser authenticationUser,
     @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
     @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc) {
        log.info("특정 매장 모든 리뷰 조회-controller");

//        PageReviewPerStoreResponseDto responseDto = adminStoreService.getReviewListPerStore(storeId, pageNum, sortBy, isDesc);
        List<ReviewPerStoreResponseDto> reviewDtoList = adminStoreService.getReviewListPerStore(authenticationUser, storeId, pageNum, isDesc);

        log.info("특정 매장 모든 리뷰 조회-controller-reviewDtoList 생성 완료");
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("조회 성공", reviewDtoList));
    }

    /**
     * 특정 매장 메뉴별 총 판매 금액 조회
     * @param storeId
     * @param user
     * @param pageNum
     * @param sortBy
     * @param isDesc
     * @return
     */
    @GetMapping("/{storeId}/earning")
    public ResponseEntity<RestApiResponse<PageTotalPricePerStoreResponseDto>> getEarning(
            @PathVariable final Long storeId,
            @AuthenticationPrincipal AuthenticationUser user,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "sortBy", required = false, defaultValue = "menu") String sortBy,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") Boolean isDesc
    ) {
        PageTotalPricePerStoreResponseDto map = adminStoreService.getEarning(pageNum, sortBy, isDesc, storeId);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("성공", map));
    }

}
