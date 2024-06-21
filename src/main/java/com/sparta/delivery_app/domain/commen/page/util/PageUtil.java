package com.sparta.delivery_app.domain.commen.page.util;

import com.sparta.delivery_app.common.exception.errorcode.PageErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {
    public static final Integer PAGE_SIZE_FIVE = 5;
    public static final Integer PAGE_SIZE_TEN = 10;

    public static Pageable createPageable(Integer pageNum, Integer pageSize, String sortBy, Boolean isAsc) {
        if (pageNum < 1) {
            throw new PageNotFoundException(PageErrorCode.INVALID_PAGE_NUMBER);
        }

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(pageNum - 1, pageSize, sort);
    }

    public static <T> void validatePage(Integer pageNum, Page<T> page) {
        if (pageNum > page.getTotalPages()) {
            throw new PageNotFoundException(PageErrorCode.INVALID_PAGE_NUMBER);
        }
    }
}
