package com.example.just_project.exchangerate.services.contract;

import com.example.just_project.exchangerate.dto.BasicCurrenciesRateDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsRate;

import java.util.Map;

public interface ExchangeRateService {

    BasicCurrenciesRateDto getBasicRatesByRuble();

    Map<?, ?> getAllRatesByRuble();                                                                                     //NOSONAR

    ExchangeRateDtoWhereRateIsRate getRatesByRuble();
}
