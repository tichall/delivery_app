package com.sparta.delivery_app.domain.common.Page;

import com.sparta.delivery_app.common.exception.errorcode.PageErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.PageNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageSortBy {
    SORT_BY_CREATED_AT("createdAt"),
    SORT_BY_STORE_NAME("storeName"),
    SORT_BY_USER_NAME("name");

    private final String sortBy;

    public static String getPageSortBy(String sortBy) {
        for (PageSortBy pageSortBy : PageSortBy.values()) {
            if (pageSortBy.sortBy.equalsIgnoreCase(sortBy)) {
                return pageSortBy.sortBy;
            }
        }
        throw new PageNotFoundException(PageErrorCode.INVALID_SORT_BY);
    }
}
