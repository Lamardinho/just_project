package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.services.ContentService;
import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.project_exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.project_exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.project_exchangerate.util.AppConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateWebService {

    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;

    public CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro() {
        val rate = objMapService.readValue(
                contentService.getContentFromUrl(AppConstants.RUBLE_CBR_DAILY_RU_URL),
                ExchangeRatesDtoWhereRateIsMapStr.class
        );
        return exchangeRateMapper.dtoWhereRateIsMapStrToCurrencyRateByUsdAndEuroDto(rate);
    }
}
