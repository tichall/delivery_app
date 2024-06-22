package com.sparta.delivery_app.domain.admin.adminstore;

import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.order.adaptor.OrderAdaptor;
import com.sparta.delivery_app.domain.order.dto.response.OrderPageResponseDto;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStoreService {

    private final MenuAdaptor menuAdaptor;
    private final OrderAdaptor orderAdaptor;
    private final StoreAdaptor storeAdaptor;


    public PageMenuPerStoreResponseDto getMenuListPerStore(
            Long storeId,Integer pageNum, String sortBy, Boolean isDesc) {

        Store choiceStore = storeAdaptor.queryStoreById(storeId);
        Pageable pageable = PageUtil.createPageable(pageNum,PageUtil.PAGE_SIZE_FIVE, sortBy,isDesc);
        Page<Menu> menuPage = menuAdaptor.queryMenuListByStoreId(storeId, pageable);
        PageUtil.validatePage(pageNum, menuPage);

        return PageMenuPerStoreResponseDto.of(pageNum, choiceStore);
    }

//   orderAdaptor.queryOrderListByStoreId(storeId);
//   List<OrderId> OrderIdList = OrderRepository.findAll(storeId);
//   for(orderId : OrderIdList ) {
//        List<Review> SpecificStoreReview = ReviewRepository.findByOrderId(OrderId)
//    }
}
