package com.sparta.delivery_app.domain.menu.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MenuAddRequestDto {

    @NotBlank(message = "Store ID가 입력되지 않았습니다.")
    private Long storeId;

    @NotBlank(message = "메뉴명이 입력되지 않았습니다.")
    @Size(max = 100)
    private String menuName;

    @NotBlank(message = "가격이 입력되지 않았습니다.")
    private Long menuPrice;

    @NotBlank(message = "메뉴 정보가 입력되지 않았습니다.")
    @Size(max = 255)
    private String menuInfo;

    private String menuImagePath;
}
