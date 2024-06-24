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
import com.sparta.delivery_app.domain.s3.util.S3Utils;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


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
    @Transactional
    public MenuAddResponseDto addMenu(MultipartFile file, final MenuAddRequestDto requestDto, AuthenticationUser user) {
        User findUser = userAdaptor.queryUserByEmailAndStatus(user.getUsername());

        Store findStore = storeAdaptor.queryStoreId(findUser);

        Menu menu = Menu.saveMenu(findStore, requestDto);
        menuAdaptor.saveMenu(menu); // 파일 경로를 메뉴 아이디로 지정해주기 위해 저장

        // 파일이 존재한다면
        if (S3Utils.isFileExists(file)) {
            try {
                String menuImagePath = s3Uploader.saveMenuImage(file, findStore.getId(), menu.getId()); // 파일을 S3에 업로드한 후 그 링크 반환
                menu.updateMenuImagePath(menuImagePath); // 반환받은 링크를 menu DB에 저장
            } catch(S3Exception e) { // 파일 업로드 중 오류가 발생한 경우
                menuAdaptor.deleteTempMenu(menu); // 저장되어 있는 메뉴 데이터 삭제
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
    public MenuModifyResponseDto modifyMenu(MultipartFile file, final Long menuId, final MenuModifyRequestDto requestDto, AuthenticationUser user) {
        User findUser = userAdaptor.queryUserByEmailAndStatus(user.getUsername());

        Store store = storeAdaptor.queryStoreId(findUser);
        Menu menu = menuAdaptor.queryMenuByIdAndMenuStatus(menuId);

        menu.checkStoreMenuMatch(menu, store.getId());
        if (S3Utils.isFileExists(file)) {
            try {
                String menuImagePath = s3Uploader.saveMenuImage(file, store.getId(), menu.getId());
                menu.updateMenuImagePath(menuImagePath);
            } catch(S3Exception e) {
                throw new S3Exception(e.getErrorCode());
            }
        }

        Menu updateMenu = menu.updateMenu(requestDto);
        return MenuModifyResponseDto.of(updateMenu);
    }

    /**
     * 메뉴 삭제
     * @param menuId
     * @param user
     */
    @Transactional
    public void deleteMenu(final Long menuId, AuthenticationUser user) {
        User findUser = userAdaptor.queryUserByEmailAndStatus(user.getUsername());

        Store store = storeAdaptor.queryStoreId(findUser);
        Menu menu = menuAdaptor.queryMenuByIdAndMenuStatus(menuId);

        menu.checkStoreMenuMatch(menu, store.getId());
        menu.deleteMenu();
    }
}