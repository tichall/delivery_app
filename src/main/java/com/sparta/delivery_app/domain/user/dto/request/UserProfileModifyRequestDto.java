package com.sparta.delivery_app.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record UserProfileModifyRequestDto(

        @NotBlank(message = "닉네임을 입력해 주세요")
        @Length(min = 3, max = 20, message = "닉네임이 조건에 맞지 않습니다.")
        String nickName,
        @NotBlank(message = "이름을 입력해 주세요")
        @Length(min = 2, max = 10, message = "성함이 조건에 맞지 않습니다.")
        String name,
        @NotBlank(message = "주소를 입력해 주세요")
        String address,
        @NotBlank(message = "비밀번호의 입력 값이 없습니다.")
        String password
) {
}
