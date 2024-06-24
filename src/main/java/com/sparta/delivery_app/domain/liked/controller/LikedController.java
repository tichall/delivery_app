package com.sparta.delivery_app.domain.liked.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.liked.service.LikedService;
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
     * 좋아요 등록
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/{storeId}")
    public ResponseEntity<RestApiResponse<Object>> likedAdd(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long storeId
    ) {

        likedService.addLiked(user, storeId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("관심 매장으로 등록되었습니다."));
    }

    /**
     * 좋아요 삭제
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<RestApiResponse<Object>> likedDelete(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long storeId
    ) {
        likedService.deleteLiked(user, storeId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("관심 매장 등록이 취소되었습니다."));

    }
}
