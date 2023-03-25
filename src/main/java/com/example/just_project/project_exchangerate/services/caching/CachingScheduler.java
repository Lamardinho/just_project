package com.example.just_project.project_exchangerate.services.caching;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.common.util.CacheNames;
import com.example.just_project.project_exchangerate.services.ratings.ExchangeRateWebService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CachingScheduler {

    @NonNull
    private final CachingService cachingService;
    @NonNull
    private final ExchangeRateWebService exchangeRateWebService;

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.DAYS)
    @TrackExecutionTime
    public void clearAllCaches() {
        cachingService.clearAllCaches();
    }

    @Scheduled(fixedRate = 360, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void updateGetActualRubleRatesByUsdAndEuro() {
        if (cachingService.clearCache(CacheNames.GET_ACTUAL_RUBLE_RATES_BY_USD_AND_EURO)) {
            exchangeRateWebService.getActualRubleRatesByUsdAndEuro();
        }
    }

    /**
     * Очищает кэш и заполняет его только для сегодняшнего дня.
     */
    @Scheduled(fixedRate = 30, initialDelay = 29, timeUnit = TimeUnit.DAYS)
    public void updateGetRubleRatesFromCbrXmlUrlByDate() {
        cachingService.clearCache(CacheNames.GET_RUBLE_RATES_FROM_CBR_XML_URL_BY_DATE);
    }

    /**
     * Очищает кэш и заполняет его только для сегодняшнего дня.
     */
    @Scheduled(fixedRate = 30, initialDelay = 29, timeUnit = TimeUnit.DAYS)
    public void updateGetRubleRatesFromCbrXmlUrlByDateFeignClient() {
        cachingService.clearCache(CacheNames.GET_RUBLE_RATES_FROM_CBR_XML_URL_BY_DATE_FEIGN_CLIENT);
    }
}
