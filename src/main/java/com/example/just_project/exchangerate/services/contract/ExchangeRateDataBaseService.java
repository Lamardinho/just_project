package com.example.just_project.exchangerate.services.contract;

import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ExchangeRateDataBaseService {

    @Transactional
    void createOrUpdate();

    List<CurrencyRateByUsdAndEuroDto> getAllCurrencyRateByUsdAndEuroDtoList(Pageable pageable);
}
