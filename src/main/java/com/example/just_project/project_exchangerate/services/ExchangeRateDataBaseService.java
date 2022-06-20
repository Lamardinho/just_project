package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.services.ContentService;
import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.project_exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.project_exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.project_exchangerate.enums.ERate;
import com.example.just_project.project_exchangerate.model.ExchangeRate;
import com.example.just_project.project_exchangerate.repositories.ExchangeRateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.just_project.util.CurrencyCalculateHelper.calculateToRub;

/**
 * Сервис для работы с рейтингами валют
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateDataBaseService {

    @NonNull
    private final ExchangeRateRepository rateRepository;
    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;

    /**
     * Нам не надо несколько рейтингов одного дня.
     * Чтобы не создавать новые рейтинги одного дня, то просто обновляем последний, иначе создаём новый.
     */
    @Transactional
    public void createOrUpdate(String url, ERate rate) {
        val dto = objMapService.readValue(
                contentService.getContentFromUrl(url),
                ExchangeRatesDtoWhereRateIsMapStr.class
        );
        val rateToMap = rateRepository
                .findByBaseAndDate(rate, dto.getDate())      //for update
                .orElse(new ExchangeRate());                   //for create

        val rateToSave = exchangeRateMapper.toExchangeRate(dto, rateToMap);

        rateRepository.save(rateToSave);
    }

    public List<CurrencyRateByUsdAndEuroDto> getAllCurrencyRateByUsdAndEuroDtoList(Pageable pageable) {
        val exchangeRates = rateRepository.findAll(pageable);
        return exchangeRates
                .stream()
                .map(it ->
                        {
                            val ratesMap = objMapService.readValueToMap(it.getRates());
                            return exchangeRateMapper.toCurrencyRateByUsdAndEuroDto(it)
                                    .setUsd(calculateToRub((double) ratesMap.get(ERate.USD.name())))
                                    .setEuro(calculateToRub((double) ratesMap.get(ERate.EUR.name())));
                        }
                )
                .collect(Collectors.toList());
    }
}
