package com.example.just_project.exchangerate.services.contract;

import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;

public interface ExchangeRateWebService {

    CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro();
}
