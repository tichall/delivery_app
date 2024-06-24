package com.sparta.delivery_app.common.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BucketConfig {

    private static final int tokens = 5; // 시간 당 생성되는 토큰 개수
    private static final int seconds = 60; // 토큰 생성 주기
    private static final int capacity = 10; // 토큰 저장소 크기

    @Bean
    public Bucket bucket() {
        final Refill refill = Refill.intervally(tokens, Duration.ofSeconds(seconds));

        final Bandwidth limit = Bandwidth.classic(capacity, refill);

        return Bucket.builder().addLimit(limit).build();
    }
}
