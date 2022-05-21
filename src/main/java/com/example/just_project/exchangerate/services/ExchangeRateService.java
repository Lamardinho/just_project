package com.example.just_project.exchangerate.services;

import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.services.contract.ContentService;
import com.example.just_project.exchangerate.dto.RubleRateDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsMapStr;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsRate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.example.just_project.exchangerate.enums.ERate.EUR;
import static com.example.just_project.exchangerate.enums.ERate.USD;
import static com.example.just_project.util.CurrencyHelper.calculateToRub;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateService {

    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final ContentService contentService;

    private static final int C_TIMEOUT = 8_000;
    private static final String RUBLE_URL = "https://www.cbr-xml-daily.ru/latest.js";

    public RubleRateDto getUsdAndEuroRateByRuble() {
        val rate = objMapService.readValue(
                contentService.getContentFromUrl(RUBLE_URL, C_TIMEOUT, C_TIMEOUT),
                ExchangeRateDtoWhereRateIsMapStr.class
        );

        return new RubleRateDto(
                rate.getDisclaimer(),
                rate.getDate(),
                rate.getBase(),
                calculateToRub(rate.getRates().get(USD.name())),
                calculateToRub(rate.getRates().get(EUR.name()))
        );
    }

    public Map<?, ?> getAllRatesByRuble() {     //NOSONAR
        return objMapService.readValueToMap(
                contentService.getContentFromUrl(RUBLE_URL, C_TIMEOUT, C_TIMEOUT)
        );
    }

    public ExchangeRateDtoWhereRateIsRate getRate() {
        return objMapService.readValue(
                contentService.getContentFromUrl(RUBLE_URL, C_TIMEOUT, C_TIMEOUT),
                ExchangeRateDtoWhereRateIsRate.class);
    }
}
