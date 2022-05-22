package com.example.just_project.exchangerate.services;

import com.example.just_project.common.services.contract.ContentService;
import com.example.just_project.common.services.contract.ObjectMapperService;
import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.exchangerate.model.ExchangeRate;
import com.example.just_project.exchangerate.repositories.ExchangeRateRepository;
import com.example.just_project.exchangerate.services.contract.ExchangeRateDataBaseService;
import com.example.just_project.util.CurrencyHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.just_project.exchangerate.enums.ERate.EUR;
import static com.example.just_project.exchangerate.enums.ERate.USD;
import static com.example.just_project.exchangerate.util.AppConstants.RUBLE_CBR_DAILY_RU_URL;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateDataBaseServiceImpl implements ExchangeRateDataBaseService {

    @NonNull
    private final ExchangeRateRepository rateRepository;
    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;

    @Override
    public void create() {
        val dto = objMapService.readValue(
                contentService.getContentFromUrl(RUBLE_CBR_DAILY_RU_URL, 8000, 8000),
                ExchangeRatesDtoWhereRateIsMapStr.class
        );
        val exchangeRate = exchangeRateMapper.toExchangeRate(dto, new ExchangeRate());
        rateRepository.save(exchangeRate);
    }

    @Override
    public List<CurrencyRateByUsdAndEuroDto> findAll() {
        val exchangeRateList = rateRepository.findAll();
        return exchangeRateList
                .stream()
                .map(exchangeRateMapper::toCurrencyRateByUsdAndEuroDto)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyRateByUsdAndEuroDto findByIds() {// TODO: 22.05.2022
        val exchangeRate = rateRepository.findAll().stream().findFirst().orElseThrow();// TODO: 22.05.2022 elseThrow //NOSONAR
        val ratesMap = objMapService.readValueToMap(exchangeRate.getRates());

        return exchangeRateMapper.toCurrencyRateByUsdAndEuroDto(exchangeRate)
                .setUsd(CurrencyHelper.calculateToRub((double) ratesMap.get(USD.name())))
                .setEuro(CurrencyHelper.calculateToRub((double) ratesMap.get(EUR.name())));
    }
}
