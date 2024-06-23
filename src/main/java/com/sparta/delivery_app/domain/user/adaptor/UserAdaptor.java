package com.sparta.delivery_app.domain.user.adaptor;

import com.sparta.delivery_app.common.globalcustomexception.UnableOpenStoreException;
import com.sparta.delivery_app.common.globalcustomexception.UserDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.UserNotExistException;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import com.sparta.delivery_app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static com.sparta.delivery_app.common.exception.errorcode.UserErrorCode.*;

@Component
@RequiredArgsConstructor
public class UserAdaptor {

    private final UserRepository userRepository;

    public void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(u -> {
                            throw new UserDuplicatedException(DUPLICATED_USER);
                        }
                );
    }

    /*
     * @throws UserNotExistException if(회원가입을 하지 않은 경우)
     */
    public User checkManagerRole(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistException(NOT_SIGNED_UP_USER)
        );
        isManagerAndEnable(user);
        return user;
    }

    /*
     * MANAGER 이면서 ENABLE 상태인지 확인
     */
    public void isManagerAndEnable(User user) {
        if (!(user.getUserRole().equals(UserRole.MANAGER) &&
                user.getUserStatus().equals(UserStatus.ENABLE))) {
            throw new UnableOpenStoreException(NOT_AUTHORITY_TO_REGISTER_STORE);
        }
    }

    /**
     * 특정 email 조회
     * Status
     */
    public User queryUserByEmailAndStatus(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException(NOT_SIGNED_UP_USER));
        UserStatus.checkUserStatus(user.getUserStatus());
        return user;
    }

    public User queryUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException(NOT_SIGNED_UP_USER));

    }

    public Page<User> queryAllUserPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getCurrentUser() {
        return null;
    }

    public User saveUser(User userData) {
        return userRepository.save(userData);
    }

//    public List<AdminUserResponseDto> queryAllUser() {
//        return userRepository.findAll().stream().map(AdminUserResponseDto::new).toList();
//    }

}
