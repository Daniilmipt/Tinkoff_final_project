package com.example.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterTravelConf {
    @Bean("avia")
    public RateLimiter rateLimiterAvia() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(1_000_000)
                .limitRefreshPeriod(Duration.ofDays(30))
                .timeoutDuration(Duration.ofSeconds(5))
                .build();

        return RateLimiter.of("aviaRateLimiter", config);
    }

    @Bean("hotel")
    public RateLimiter rateLimiterHotel() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(60)
                .limitRefreshPeriod(Duration.ofSeconds(60))
                .timeoutDuration(Duration.ofSeconds(5))
                .build();

        return RateLimiter.of("hotelRateLimiter", config);
    }
}
