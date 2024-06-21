package com.sparta.delivery_app.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery_app.common.security.AuthenticationUserService;
import com.sparta.delivery_app.common.security.jwt.*;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationUserService authenticationUserService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    private final JwtLogoutHandler jwtLogoutHandler;
    private final UserAdaptor userAdaptor;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(objectMapper, jwtUtil, userAdaptor);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, authenticationUserService, userAdaptor);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(HttpMethod.POST, "/api/v1/users/consumers").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users/managers").permitAll()
                .requestMatchers(HttpMethod.POST, "/open-api/**").permitAll()
                .anyRequest().authenticated()
        );

        //security filter
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        //예외 검증
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        );

        return http.build();
    }
}