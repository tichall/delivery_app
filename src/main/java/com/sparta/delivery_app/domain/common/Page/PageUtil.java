package com.sparta.delivery_app.domain.common.Page;

import com.sparta.delivery_app.common.exception.errorcode.PageErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.sparta.delivery_app.domain.common.Page.PageConstants.*;

public class PageUtil {

    public static Pageable createPageable(Integer pageNum, Integer pageSize, Boolean isDesc) {
        if (pageNum < 1) {
            throw new PageNotFoundException(PageErrorCode.INVALID_PAGE_NUMBER);
        }

        Sort.Direction direction = isDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, SORT_BY_CREATED_AT);

        return PageRequest.of(pageNum - 1, pageSize, sort);
    }

    public static <T> String validateAndSummarizePage(Integer pageNum, Page<T> page) {
        if (page.getTotalElements() <= 0) {
            return NO_ELEMENT_MESSAGE;
        }

        if (pageNum > page.getTotalPages()) {
            throw new PageNotFoundException(PageErrorCode.INVALID_PAGE_NUMBER);
        }

        return page.getTotalElements() + "개 조회 완료!";
    }
}
