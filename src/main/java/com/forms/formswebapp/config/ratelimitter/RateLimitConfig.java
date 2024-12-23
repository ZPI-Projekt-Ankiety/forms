package com.forms.formswebapp.config.ratelimitter;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
class RateLimitConfig {

    @Bean
    Bucket bucket() {
        Bandwidth limit = Bandwidth.classic(300, Refill.greedy(300, Duration.ofMinutes(1)));
        return Bucket4j.builder().addLimit(limit).build();
    }
}