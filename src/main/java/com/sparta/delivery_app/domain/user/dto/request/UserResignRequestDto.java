package com.sparta.delivery_app.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserResignRequestDto(
        @NotBlank(message = "비밀번호의 입력 값이 없습니다.")
        String password
) {

}
