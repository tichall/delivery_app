package com.sparta.delivery_app.common.security;


import com.sparta.delivery_app.domain.user.entity.PasswordHistory;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationUser implements UserDetails {

    /**
     * User Entity email
     */
    private final String email;
    /**
     * User Entity password
     */
    private final String password;
    /**
     * User Entity userRole
     */
    @Getter
    private UserRole userRole;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole.getUserRoleName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public static AuthenticationUser of(User user, PasswordHistory passwordHistory) {
        return AuthenticationUser.builder()
                .email(user.getEmail())
                .password(passwordHistory.getPassword())
                .userRole(user.getUserRole())
                .build();
    }
}