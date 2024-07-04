package com.sparta.delivery_app.domain.liked.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.liked.dto.LikedStorePageResponseDto;
import com.sparta.delivery_app.domain.liked.service.LikedService;
import com.sparta.delivery_app.domain.openApi.dto.ReviewPageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/liked")
public class LikedController {

    private final LikedService likedService;

    /**
     * 매장 좋아요 등록 및 취소
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<RestApiResponse<Void>> storeLikedAdd(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long storeId
    ) {
        Boolean isLiked = likedService.addStoreLiked(user, storeId);
        if (isLiked){
            return ResponseEntity.status(StatusCode.OK.code)
                    .body(RestApiResponse.of("관심 매장으로 등록되었습니다."));
        } else {
            return ResponseEntity.status(StatusCode.OK.code)
                    .body(RestApiResponse.of("관심 매장 등록이 취소되었습니다."));
        }
    }

    /**
     * 좋아요한 매장 전체 조회
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/stores")
    public ResponseEntity<RestApiResponse<LikedStorePageResponseDto>> getLikedStores(@AuthenticationPrincipal AuthenticationUser user, @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum, @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc                                                        ) {
        LikedStorePageResponseDto responseDto = likedService.getLikedStores(user, pageNum, isDesc);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of(responseDto));
    }

    /**
     * 유저 리뷰 좋아요 등록 및 취소
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/reviews/{reviewId}")
    public ResponseEntity<RestApiResponse<Void>> userReviewLikedAdd(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long reviewId
    ) {
        Boolean isLiked = likedService.addReviewLiked(user, reviewId);
        if (isLiked){
            return ResponseEntity.status(StatusCode.OK.code)
                    .body(RestApiResponse.of("리뷰 좋아요가 완료되었습니다."));
        } else {
            return ResponseEntity.status(StatusCode.OK.code)
                    .body(RestApiResponse.of("리뷰 좋아요가 취소되었습니다."));
        }
    }

    /**
     * 좋아요한 유저 리뷰 전체 조회
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/userReviews")
    public ResponseEntity<RestApiResponse<ReviewPageResponseDto>> getLikedUserReviews(
            @AuthenticationPrincipal AuthenticationUser user,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc
    ) {

        ReviewPageResponseDto responseDto = likedService.getLikedUserReviews(user, pageNum, isDesc);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("좋아요한 리뷰 조회에 성공했습니다.", responseDto));
    }

}
