package com.example.just_project.exchangerate.services.contract;

import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.model.ExchangeRate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ExchangeRateDataBaseService {

    @Transactional
    void create();

    List<ExchangeRate> getAllEntity();

    List<CurrencyRateByUsdAndEuroDto> getAllDtoList();

    ExchangeRate getLastEntity();

    CurrencyRateByUsdAndEuroDto getLastDto();
}
