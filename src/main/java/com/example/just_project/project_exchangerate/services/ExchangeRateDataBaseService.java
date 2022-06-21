package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.services.ContentService;
import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.services.XmlMapperService;
import com.example.just_project.common.util.AppException;
import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import com.example.just_project.project_exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.project_exchangerate.enums.ERate;
import com.example.just_project.project_exchangerate.model.ExchangeRate;
import com.example.just_project.project_exchangerate.repositories.DataSourceRepository;
import com.example.just_project.project_exchangerate.repositories.ExchangeRateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.just_project.project_exchangerate.enums.ESource.CBR_RU_DAILY_ENG_XML;
import static com.example.just_project.project_exchangerate.util.ExchangeErrors.DATA_SOURCE_NOT_FOUND;
import static java.lang.String.format;

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
    private final XmlMapperService xmlMapperService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;
    @NonNull
    private final DataSourceRepository dataSourceRepository;

    /**
     * Нам не надо несколько рейтингов одного дня.
     * Чтобы не создавать новые рейтинги одного дня, то просто обновляем последний, иначе создаём новый.
     */
    /*@Transactional
    public void createOrUpdateFromJson(String url, ERate rate) {
        val dto = objMapService.readValue(
                contentService.getContentFromUrl(url),
                ExchangeRatesDtoWhereRateIsMapStr.class
        );
        val rateToMap = rateRepository
                .findByBaseAndDate(rate, dto.getDate())      //for update
                .orElse(new ExchangeRate());                   //for create

        val rateToSave = exchangeRateMapper.toExchangeRate(dto, rateToMap);

        rateRepository.save(rateToSave);
    }*/

    /**
     * Нам не надо несколько рейтингов одного дня.
     * Чтобы не создавать новые рейтинги одного дня, то просто обновляем последний, иначе создаём новый.
     */
    @Transactional
    @SneakyThrows
    public void createOrUpdateFromCbrXml() {
        val url = CBR_RU_DAILY_ENG_XML.getUrl();
        val currency = ERate.RUB;
        val valCurs = xmlMapperService.readXml(new URL(url), ValCurs.class);
        val dateOfRating = LocalDate.parse(valCurs.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        val rateToMap = rateRepository
                .findByCurrencyAndDateRating(currency, dateOfRating)     //for update
                .orElse(new ExchangeRate());                 //for create

        val result = new ExchangeRate(
                rateToMap.getId(),
                dataSourceRepository
                        .findBySource(CBR_RU_DAILY_ENG_XML)
                        .orElseThrow(() -> new AppException(format(DATA_SOURCE_NOT_FOUND, CBR_RU_DAILY_ENG_XML))),
                dateOfRating,
                valCurs.getTime(),
                currency,
                objMapService.writeValueAsString(valCurs.getCurrencies())
        );

        rateRepository.save(result);
    }

    /*public List<CurrencyRateByUsdAndEuroDto> getAllCurrencyRateByUsdAndEuroDtoList(Pageable pageable) {
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
    }*/
}
