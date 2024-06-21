package com.sparta.delivery_app.domain.user.adaptor;

import com.sparta.delivery_app.common.globalcustomexception.UnableOpenStoreException;
import com.sparta.delivery_app.common.globalcustomexception.UserDuplicatedException;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.common.globalcustomexception.UserNotExistException;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public User checkManagerRole(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistException(NOT_SIGNED_UP_USER)
        );
        if (!user.getUserRole().equals(UserRole.CONSUMER) || (!user.getUserRole().equals(UserRole.ADMIN))) {
            throw new UnableOpenStoreException(NOT_USER);
        }
        return user;

    }

    public User queryUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException(NOT_SIGNED_UP_USER));
    }

    public User saveUser(User userData) {
        return userRepository.save(userData);
    }
}
