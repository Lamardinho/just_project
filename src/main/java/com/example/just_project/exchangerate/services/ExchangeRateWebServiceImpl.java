package com.example.just_project.exchangerate.services;

import com.example.just_project.common.services.contract.ContentService;
import com.example.just_project.common.services.contract.ObjectMapperService;
import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.exchangerate.services.contract.ExchangeRateWebService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.just_project.exchangerate.util.AppConstants.RUBLE_CBR_DAILY_RU_URL;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateWebServiceImpl implements ExchangeRateWebService {

    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;

    @Override
    public CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro() {
        val rate = objMapService.readValue(
                contentService.getContentFromUrl(RUBLE_CBR_DAILY_RU_URL, 8000, 8000),
                ExchangeRatesDtoWhereRateIsMapStr.class
        );
        return exchangeRateMapper.dtoWhereRateIsMapStrToCurrencyRateByUsdAndEuroDto(rate);
    }
}
