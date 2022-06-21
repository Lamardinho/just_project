package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.services.ContentService;
import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.project_exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.project_exchangerate.dtomappers.ExchangeRateMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.just_project.project_exchangerate.enums.ESource.CBR_NOT_OFFICIAL_XML_DAILY_RU_LATEST_JSON;

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
                contentService.getContentFromUrl(CBR_NOT_OFFICIAL_XML_DAILY_RU_LATEST_JSON.getUrl()),
                ExchangeRatesDtoWhereRateIsMapStr.class
        );
        return exchangeRateMapper.dtoWhereRateIsMapStrToCurrencyRateByUsdAndEuroDto(rate);
    }
}
