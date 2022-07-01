package com.example.just_project.project_exchangerate.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CachingService {

    @NonNull
    private final CacheManager cacheManager;

    /**
     * Автоматически запускает очистку кэша через определенный промежуток времени
     */
    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.MINUTES)
    public void schedulerToClearAllCaches() {
        clearAllCaches();
        log.info("run schedulerToClearAllCaches");
    }

    /**
     * Очищает весь кеш
     */
    public void clearAllCaches() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
        log.info("all caches cleared");
    }
}
