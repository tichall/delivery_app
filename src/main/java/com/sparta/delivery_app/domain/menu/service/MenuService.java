package com.sparta.delivery_app.domain.menu.service;

import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.S3Exception;
import com.sparta.delivery_app.common.globalcustomexception.StoreMenuMismatchException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.dto.request.MenuAddRequestDto;
import com.sparta.delivery_app.domain.menu.dto.request.MenuModifyRequestDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuAddResponseDto;
import com.sparta.delivery_app.domain.menu.dto.response.MenuModifyResponseDto;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.s3.service.S3Uploader;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.sparta.delivery_app.domain.user.entity.UserStatus.checkManagerEnable;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuAdaptor menuAdaptor;
    private final StoreAdaptor storeAdaptor;
    private final UserAdaptor userAdaptor;
    private final S3Uploader s3Uploader;

    /**
     * 메뉴 등록
     * @param requestDto
     * @return responseDto
     */
    public MenuAddResponseDto addMenu(MultipartFile file, final MenuAddRequestDto requestDto, AuthenticationUser user) {
        User findUser = checkUserAuth(user);

        Store findStore = storeAdaptor.queryStoreId(findUser);

        Menu menu = Menu.of(findStore, requestDto);
        menuAdaptor.saveMenu(menu);

        if (s3Uploader.isFileExists(file)) {
            try {
                String menuImagePath = s3Uploader.saveMenuImage(file, findStore.getId(), menu.getId());
                menu.updateMenuImagePath(menuImagePath);
            } catch(S3Exception e) {
                menuAdaptor.deleteTempMenu(menu);
                throw new S3Exception(e.getErrorCode());
            }
        }
        return MenuAddResponseDto.of(menu);
    }

    /**
     * 메뉴 수정
     * @param menuId
     * @param requestDto
     * @param user
     * @return responseDto
     */
    @Transactional
    public MenuModifyResponseDto modifyMenu(Long menuId, final MenuModifyRequestDto requestDto, AuthenticationUser user) {
        User findUser = checkUserAuth(user);

        Store store = storeAdaptor.queryStoreId(findUser);
        Menu menu = menuAdaptor.queryMenuByIdAndMenuStatus(menuId);

        checkStoreMenuMatch(menu, store.getId());
        menu.updateMenu(requestDto);

        return MenuModifyResponseDto.of(menu);
    }

    /**
     * 메뉴 삭제
     * @param menuId
     * @param user
     */
    @Transactional
    public void deleteMenu(Long menuId, AuthenticationUser user) {
        User findUser = checkUserAuth(user);

        Store store = storeAdaptor.queryStoreId(findUser);
        Menu menu = menuAdaptor.queryMenuByIdAndMenuStatus(menuId);

        checkStoreMenuMatch(menu, store.getId());
        menu.deleteMenu();
    }

    /**
     * 해당 메뉴가 사용자의 매장에 등록된 메뉴가 맞는지 검증
     * @param menu
     * @param storeId
     */
    public void checkStoreMenuMatch(Menu menu, Long storeId) {
        if(!menu.getStore().getId().equals(storeId)) {
            throw new StoreMenuMismatchException(OrderErrorCode.STORE_MENU_MISMATCH);
        }
    }

    /**
     * 사용자 인증
     * @param user
     * @return
     */
    private User checkUserAuth(AuthenticationUser user) {
        User findUser = userAdaptor.queryUserByEmailAndStatus(user.getUsername());
        checkManagerEnable(findUser);

        return findUser;
    }
}