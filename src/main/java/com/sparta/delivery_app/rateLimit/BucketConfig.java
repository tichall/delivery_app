package com.sparta.delivery_app.rateLimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BucketConfig {

    @Bean
    public Bucket bucket() {
        final Refill refill = Refill.intervally(3, Duration.ofSeconds(60));

        final Bandwidth limit = Bandwidth.classic(3, refill);

        return Bucket.builder().addLimit(limit).build();
    }
}
