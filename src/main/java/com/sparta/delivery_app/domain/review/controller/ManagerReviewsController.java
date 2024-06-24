package com.sparta.delivery_app.domain.review.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewModifyRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.ManagerReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.ManagerReviewAddResponseDto;
import com.sparta.delivery_app.domain.review.dto.response.ManagerReviewModifyResponseDto;
import com.sparta.delivery_app.domain.review.service.ManagerReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews/managers")
public class ManagerReviewsController {

    private final ManagerReviewsService managerReviewsService;

    @PostMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<ManagerReviewAddResponseDto>> create(
            @PathVariable final Long orderId,
            @Valid @RequestBody final ManagerReviewAddRequestDto RequestDto,
            @AuthenticationPrincipal AuthenticationUser user
    ) {

        ManagerReviewAddResponseDto responseDto = managerReviewsService.addReview(orderId, RequestDto, user);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("리뷰가 등록되었습니다.", responseDto));
    }


    @PutMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<ManagerReviewModifyResponseDto>> update(
            @PathVariable final Long orderId,
            @Valid @RequestBody final ManagerReviewModifyRequestDto RequestDto,
            @AuthenticationPrincipal AuthenticationUser user
    ) {
        ManagerReviewModifyResponseDto responseDto = managerReviewsService.modifyReview(orderId, RequestDto, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 수정되었습니다.", responseDto));
    }
}
