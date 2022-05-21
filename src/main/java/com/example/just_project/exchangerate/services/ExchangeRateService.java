package com.example.just_project.exchangerate.services;

import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.services.contract.ContentService;
import com.example.just_project.exchangerate.dto.RubleRateDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsMapStr;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsRate;
import com.example.just_project.exchangerate.dtomappers.ExchangeRateMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateService {

    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;

    private static final int C_TIMEOUT = 8_000;
    private static final String RUBLE_URL = "https://www.cbr-xml-daily.ru/latest.js";

    public RubleRateDto getUsdAndEuroRateByRuble() {
        val rate = objMapService.readValue(getRubleContentFromUrl(), ExchangeRateDtoWhereRateIsMapStr.class);
        return exchangeRateMapper.exchangeRateDtoWhereRateIsMapStrToRubleRateDto(rate);
    }

    public Map<?, ?> getAllRatesByRuble() {                                                                             //NOSONAR
        return objMapService.readValueToMap(getRubleContentFromUrl());
    }

    public ExchangeRateDtoWhereRateIsRate getRate() {
        return objMapService.readValue(getRubleContentFromUrl(), ExchangeRateDtoWhereRateIsRate.class);
    }

    private String getRubleContentFromUrl() {
        return contentService.getContentFromUrl(RUBLE_URL, C_TIMEOUT, C_TIMEOUT);
    }
}
