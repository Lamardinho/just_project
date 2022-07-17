package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.common.util.CacheNames;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CachingService {

    @NonNull
    private final CacheManager cacheManager;
    @NonNull
    private final ExchangeRateWebService exchangeRateWebService;
    @NonNull
    private final AsyncTestStudyService asyncTestStudyService;

    @TrackExecutionTime
    public Collection<String> getCacheNames() {
        val cacheNames = cacheManager.getCacheNames();
        asyncTestStudyService.testAsyncMethod();    // just for test @Async
        return cacheNames;
    }

    public void clearAllCaches() {
        val cacheNames = cacheManager.getCacheNames();
        cacheNames.forEach(this::clearCache);
        log.info("All caches cleared");
    }

    private boolean clearCache(@NonNull String cacheName) {
        var result = false;
        val cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
            result = true;
            log.info("cache: " + cacheName + " cleared");
        }
        return result;
    }

    /**
     * Автоматически запускает очистку всех кэшей через определенный промежуток времени
     */
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.DAYS)
    public void schedulerToClearAllCaches() {
        clearAllCaches();
    }

    @Scheduled(fixedRate = 360, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void updateGetActualRubleRatesByUsdAndEuro() {
        if (clearCache(CacheNames.GET_ACTUAL_RUBLE_RATES_BY_USD_AND_EURO)) {
            exchangeRateWebService.getActualRubleRatesByUsdAndEuro();
        }
    }

    /**
     * Очищает кэш и заполняет его только для сегодняшнего дня.
     */
    @Scheduled(fixedRate = 30, initialDelay = 29, timeUnit = TimeUnit.DAYS)
    public void updateGetRubleRatesFromCbrXmlUrlByDate() {
        clearCache(CacheNames.GET_RUBLE_RATES_FROM_CBR_XML_URL_BY_DATE);
    }

    /**
     * Очищает кэш и заполняет его только для сегодняшнего дня.
     */
    @Scheduled(fixedRate = 30, initialDelay = 29, timeUnit = TimeUnit.DAYS)
    public void updateGetRubleRatesFromCbrXmlUrlByDateFeignClient() {
        clearCache(CacheNames.GET_RUBLE_RATES_FROM_CBR_XML_URL_BY_DATE_FEIGN_CLIENT);
    }
}
