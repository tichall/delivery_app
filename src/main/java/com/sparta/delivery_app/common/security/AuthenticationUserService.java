package com.sparta.delivery_app.common.security;

import com.sparta.delivery_app.domain.user.adaptor.PasswordHistoryAdaptor;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.PasswordHistory;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j(topic = "유저검증")
@Service
@RequiredArgsConstructor
public class AuthenticationUserService implements UserDetailsService {

    private final UserAdaptor userAdaptor;
    private final PasswordHistoryAdaptor passwordHistoryAdaptor;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userAdaptor.queryUserByEmail(email);
        PasswordHistory passwordHistory = passwordHistoryAdaptor.queryPasswordHistoryTop1ByUser(user);
        return AuthenticationUser.of(user, passwordHistory);
    }
}