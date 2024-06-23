package com.sparta.delivery_app.domain.admin.adminstore.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.admin.adminstore.service.AdminStoreService;
import com.sparta.delivery_app.domain.admin.adminstore.dto.PageMenuPerStoreResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
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
     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
     @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
     @RequestParam(value = "isDesc", required = false, defaultValue = "true") Boolean isDesc)
    {log.info("특정매장 모든메뉴 조회");

        PageMenuPerStoreResponseDto responseDto = adminStoreService.getMenuListPerStore(storeId,
                authenticationUser,pageNum, sortBy, isDesc);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("조회 성공", responseDto));
    }


    //매장별 리뷰 다건 조회(폐업 매장과 삭제 리뷰 포함)
    // 2. 각 매장에대한 리뷰("/api/v1/admin/stores/{storeId}/reviews")
//    @GetMapping("/{storeId}/reviews")
//    public ResponseEntity<RestApiResponseM<PageReviewPerStoreResponseDto>> getReviewListPerStore
//    (@PathVariable Long storeId,
//     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
//     @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
//     @RequestParam(value = "isDesc", required = false, defaultValue = "true") Boolean isDesc)
//    {log.info("특정 매장 모든 리뷰 조회");
//
//        PageReviewPerStoreResponseDto responseDto = adminStoreService.getReviewListPerStore(storeId, pageNum, sortBy, isDesc);
//        return ResponseEntity.status(StatusCode.CREATED.code)
//                .body(RestApiResponse.of("조회 성공", responseDto));
//    }


}
