package com.example.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterTravelConf {
    @Value("${ratelimiter.avia.limit}")
    Integer aviaLimit;

    @Value("${ratelimiter.avia.period-days}")
    Long aviaPeriod;

    @Value("${ratelimiter.avia.timeout-seconds}")
    Long aviaTimeOut;

    @Value("${ratelimiter.hotel.limit}")
    Integer hotelLimit;

    @Value("${ratelimiter.hotel.period-days}")
    Long hotelPeriod;

    @Value("${ratelimiter.hotel.timeout-seconds}")
    Long hotelTimeOut;

    @Bean("avia")
    public RateLimiter rateLimiterAvia() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(aviaLimit)
                .limitRefreshPeriod(Duration.ofDays(aviaPeriod))
                .timeoutDuration(Duration.ofSeconds(aviaTimeOut))
                .build();

        return RateLimiter.of("aviaRateLimiter", config);
    }

    @Bean("hotel")
    public RateLimiter rateLimiterHotel() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(hotelLimit)
                .limitRefreshPeriod(Duration.ofSeconds(hotelPeriod))
                .timeoutDuration(Duration.ofSeconds(hotelTimeOut))
                .build();

        return RateLimiter.of("hotelRateLimiter", config);
    }
}
