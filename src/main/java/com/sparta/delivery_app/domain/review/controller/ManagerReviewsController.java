package com.sparta.delivery_app.domain.review.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.ManagerReviewResponseDto;
import com.sparta.delivery_app.domain.review.service.ManagerReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ManagerReviewsController {

    private final ManagerReviewsService managerReviewsService;

    @PostMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<ManagerReviewResponseDto>> create(
            @PathVariable Long orderId,
            @RequestBody ManagerReviewRequestDto RequestDto,
            @AuthenticationPrincipal AuthenticationUser user
    ) {

        ManagerReviewResponseDto responseDto = managerReviewsService.addReview(orderId, RequestDto, user);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("리뷰가 등록되었습니다.", responseDto));
    }


    @PutMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<ManagerReviewResponseDto>> update(
            @PathVariable Long orderId,
            @RequestBody ManagerReviewRequestDto RequestDto,
            @AuthenticationPrincipal AuthenticationUser user
    ) {
        ManagerReviewResponseDto responseDto = managerReviewsService.modifyReview(orderId, RequestDto, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 수정되었습니다.", responseDto));
    }
}
