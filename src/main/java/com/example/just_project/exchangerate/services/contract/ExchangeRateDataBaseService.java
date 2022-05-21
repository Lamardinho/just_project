package com.example.just_project.exchangerate.services.contract;

import com.example.just_project.exchangerate.dto.BasicCurrenciesRateDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ExchangeRateDataBaseService {

    @Transactional
    void create();

    BasicCurrenciesRateDto findByIds();
}
