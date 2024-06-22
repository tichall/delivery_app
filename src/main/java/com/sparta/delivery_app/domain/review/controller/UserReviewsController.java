package com.sparta.delivery_app.domain.review.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewResponseDto;
import com.sparta.delivery_app.domain.review.service.UserReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserReviewsController {

    private final UserReviewsService userReviewsService;

    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<RestApiResponse<UserReviewResponseDto>> create(
            @PathVariable Long orderId,
            @Valid @RequestBody UserReviewRequestDto requestDto) {

        UserReviewResponseDto responseDto = userReviewsService.addReview(orderId, requestDto);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("리뷰가 등록되었습니다.", responseDto));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<RestApiResponse<UserReviewResponseDto>> update(
            @PathVariable Long reviewId,
            @Valid @RequestBody UserReviewRequestDto requestDto) {

        UserReviewResponseDto responseDto = userReviewsService.modifyReview(reviewId, requestDto);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 수정되었습니다.", responseDto));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<RestApiResponse<Object>> delete(@PathVariable Long reviewId) {

        userReviewsService.deleteReview(reviewId);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 삭제되었습니다."));
    }
}
