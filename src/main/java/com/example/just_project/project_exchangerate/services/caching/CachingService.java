package com.example.just_project.project_exchangerate.services.caching;

import com.example.just_project.project_exchangerate.services.AsyncTestStudyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Log4j2
public class CachingService {

    @NonNull
    private final CacheManager cacheManager;
    @NonNull
    private final AsyncTestStudyService asyncTestStudyService;

    public Collection<String> getCacheNames() {
        val cacheNames = cacheManager.getCacheNames();
        asyncTestStudyService.testAsyncMethod();    // just for test @Async
        return cacheNames;
    }

    public void clearAllCaches() {
        val cacheNames = cacheManager.getCacheNames();
        cacheNames.forEach(this::clearCache);
    }

    public boolean clearCache(@NonNull String cacheName) {
        var result = false;
        val cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
            result = true;
            log.info("cache: " + cacheName + " cleared");
        }
        return result;
    }
}
