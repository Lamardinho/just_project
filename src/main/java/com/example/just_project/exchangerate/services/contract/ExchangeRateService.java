package com.example.just_project.exchangerate.services.contract;

import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;

import java.util.Map;

public interface ExchangeRateService {

    CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro();

    Map<?, ?> getAllRatesByCurrency(); //NOSONAR

    //ExchangeRatesDtoWhereRateIsRate getRatesByCurrency();
}
