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

import static com.example.just_project.exchangerate.util.AppConstants.RUBLE_CBR_DAILY_RU_URL;

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

    @Override
    public BasicCurrenciesRateDto getBasicRatesByRuble() {
        val rate = objMapService.readValue(
                contentService.getContentFromUrl(RUBLE_CBR_DAILY_RU_URL, 8000, 8000),
                ExchangeRateDtoWhereRateIsMapStr.class
        );
        return exchangeRateMapper.dtoWhereRateIsMapStrToBasicCurrenciesRateDto(rate);
    }

    @Override
    public Map<?, ?> getAllRatesByRuble() { //NOSONAR
        return objMapService.readValueToMap(contentService.getContentFromUrl(RUBLE_CBR_DAILY_RU_URL, 8000, 8000));
    }

    @Override
    public ExchangeRateDtoWhereRateIsRate getRatesByRuble() {
        return objMapService.readValue(
                contentService.getContentFromUrl(RUBLE_CBR_DAILY_RU_URL, 8000, 8000),
                ExchangeRateDtoWhereRateIsRate.class
        );
    }
}
