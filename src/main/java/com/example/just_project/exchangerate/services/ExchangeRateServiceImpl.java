package com.example.just_project.exchangerate.services;

import com.example.just_project.common.services.contract.ContentService;
import com.example.just_project.common.services.contract.ObjectMapperService;
import com.example.just_project.exchangerate.dto.BasicCurrenciesRateDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsMapStr;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsRate;
import com.example.just_project.exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.exchangerate.services.contract.ExchangeRateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;

    private static final int C_TIMEOUT = 8_000;
    private static final String RUBLE_URL = "https://www.cbr-xml-daily.ru/latest.js";

    @Override
    public BasicCurrenciesRateDto getBasicRatesByRuble() {
        val rate = objMapService.readValue(getRubleContentFromUrl(), ExchangeRateDtoWhereRateIsMapStr.class);
        return exchangeRateMapper.dtoWhereRateIsMapStrToBasicCurrenciesRateDto(rate);
    }

    @Override
    public Map<?, ?> getAllRatesByRuble() {                                                                             //NOSONAR
        return objMapService.readValueToMap(getRubleContentFromUrl());
    }

    @Override
    public ExchangeRateDtoWhereRateIsRate getRatesByRuble() {
        return objMapService.readValue(getRubleContentFromUrl(), ExchangeRateDtoWhereRateIsRate.class);
    }

    private String getRubleContentFromUrl() {
        return contentService.getContentFromUrl(RUBLE_URL, C_TIMEOUT, C_TIMEOUT);
    }
}