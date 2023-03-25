package com.example.just_project.project_exchangerate.services.ratings;

import com.example.just_project.project_exchangerate.util.ExchangeErrors;
import com.example.just_project.project_exchangerate.util.ExchangeRateMessages;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Log4j2
public class RatingScheduler {

    @NonNull
    private final ExchangeRateDataBaseService exchangeRateDataBaseService;

    /**
     * Automatically updates today's rating.
     * <p>
     * Автоматически обновляет сегодняшний рейтинг.
     */
    @Scheduled(fixedRate = 360, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void updateToday() {
        try {
            exchangeRateDataBaseService.createOrUpdateRubleRatesFromCbrUrlXmlByDate(LocalDate.now());
            log.info(ExchangeRateMessages.TODAY_RATINGS_UPDATE_SUCCESSFUL);
        } catch (Exception e) {
            log.error(ExchangeErrors.FAILED_TO_UPDATE_RATING + ": " + e);
        }
    }
}
