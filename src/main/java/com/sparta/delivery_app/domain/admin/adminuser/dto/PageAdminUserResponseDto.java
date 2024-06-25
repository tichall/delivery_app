package com.sparta.delivery_app.domain.admin.adminuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageAdminUserResponseDto {

    private Integer currentPage;
    private String allUserPageCount;
    private List<AdminUserResponseDto> userList;

    public static PageAdminUserResponseDto of (
            Integer currentPage, Page<AdminUserResponseDto> userPage, String totalUser) {

        return PageAdminUserResponseDto.builder()
                .currentPage(currentPage)
                .allUserPageCount(totalUser)
                .userList(userPage.stream().toList())
                .build();
    }
}
