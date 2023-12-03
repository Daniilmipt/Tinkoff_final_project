package com.example.utils;

import com.example.dto.PathDto;
import com.example.request.models.TravelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class CacheUtil {
    private final CacheManager cacheManager;

    @Value("${cache-name}")
    private String cacheName;

    public List<PathDto> getCacheValue(String cacheName, TravelRequest key) {
        Cache.ValueWrapper obj = Objects.requireNonNull(cacheManager.getCache(cacheName)).get(key);
        if (obj != null) {
            return (List<PathDto>) obj.get();
        }
        return null;
    }

    @Scheduled(fixedRateString = "${schedule.time-period-secods}")
    private void clearCache() {
        Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
    }
}
