package com.example.just_project.exchangerate.services.contract;

import com.example.just_project.exchangerate.dto.RubleRateDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsRate;

import java.util.Map;

public interface ExchangeRateService {

    RubleRateDto getUsdAndEuroRateByRuble();

    Map<?, ?> getAllRatesByRuble();                                                                                     //NOSONAR

    ExchangeRateDtoWhereRateIsRate getRate();

    String getRubleContentFromUrl();
}
