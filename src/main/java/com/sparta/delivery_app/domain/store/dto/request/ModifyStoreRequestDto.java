package com.sparta.delivery_app.domain.store.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public record ModifyStoreRequestDto (
        @NotBlank(message = "매장 이름이 입력되지 않았습니다.")
        @Size(max = 20, message = "매장명은 20자를 초과할 수 없습니다.")
        String storeName,

        @NotBlank(message = "매장 주소가 입력되지 않았습니다.")
        @Size(max = 255, message = "주소의 입력가능한 글자수를 초과하였습니다.")
        String storeAddress,

        @NotNull(message = "사업자 번호가 입력되지 않았습니다.")
        @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자 번호 형식이 올바르지 않습니다.")
        String storeRegistrationNumber,

        @NotNull(message = "최소주문 금액이 입력되지 않았습니다.")
        @Max(value = 1000000, message = "최소주문금액은 100만 원을 초과할 수 없습니다")
        Long minTotalPrice,

        @Size(max = 255, message = "입력 가능한 글자 수를 초과하였습니다.")
        String storeInfo
) {

}
