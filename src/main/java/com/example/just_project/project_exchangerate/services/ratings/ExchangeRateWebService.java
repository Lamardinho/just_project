package com.example.just_project.project_exchangerate.services.ratings;

import com.example.just_project.common.services.ContentService;
import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.services.XmlMapperService;
import com.example.just_project.common.util.CacheNames;
import com.example.just_project.project_exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import com.example.just_project.project_exchangerate.dtomappers.ExchangeRateMapper;
import com.example.just_project.project_exchangerate.services.CbrRubleRatesClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URL;
import java.time.LocalDate;

import static com.example.just_project.project_exchangerate.enums.ESource.CBR_NOT_OFFICIAL_XML_DAILY_RU_LATEST_JSON;
import static com.example.just_project.project_exchangerate.enums.ESource.CBR_RU_DAILY_ENG_XML;
import static java.time.format.DateTimeFormatter.ofPattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateWebService {

    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final XmlMapperService xmlMapperService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final ExchangeRateMapper exchangeRateMapper;
    @NonNull
    private final CbrRubleRatesClient cbrRubleRatesClient;

    /**
     * Получение сегодняшнего курса валют.
     * Источник: <a href="https://www.cbr-xml-daily.ru/latest.js">...</a>.
     * Соотношение: RUB -> Currency.
     */
    @Cacheable(cacheNames = CacheNames.GET_ACTUAL_RUBLE_RATES_BY_USD_AND_EURO)
    public CurrencyRateByUsdAndEuroDto getActualRubleRatesByUsdAndEuro() {
        val rate = objMapService.readValue(
                contentService.getContentFromUrl(CBR_NOT_OFFICIAL_XML_DAILY_RU_LATEST_JSON.getUrl()),
                ExchangeRatesDtoWhereRateIsMapStr.class
        );
        return exchangeRateMapper.toDtoWhereRatesIsMapToCurrencyRateByUsdAndEuroDto(rate);
    }

    /**
     * Получение курса валют по заданному дню (сегодняшний по умолчанию).
     * Источник <a href="https://www.cbr.ru/scripts/XML_daily_eng.asp?date_req=">...</a>.
     * Соотношение: Currency -> RUB
     */
    @Cacheable(cacheNames = CacheNames.GET_RUBLE_RATES_FROM_CBR_XML_URL_BY_DATE)
    @SneakyThrows
    public ValCurs getRubleRatesFromCbrXmlUrlByDate(LocalDate date) {
        return xmlMapperService.readXml(
                new URL(CBR_RU_DAILY_ENG_XML.getUrl() + date.format(ofPattern("dd/MM/yyyy"))),
                ValCurs.class
        );
    }

    /**
     * Аналог {@link #getRubleRatesFromCbrXmlUrlByDate(LocalDate)} реализованный через FeignClient
     */
    @Cacheable(cacheNames = CacheNames.GET_RUBLE_RATES_FROM_CBR_XML_URL_BY_DATE_FEIGN_CLIENT)
    @SneakyThrows
    public ValCurs getRubleRatesFromCbrXmlUrlByDateFeignClient(LocalDate date) {
        return cbrRubleRatesClient.getRubleRatesFromCbrXmlUrl(
                URI.create(CBR_RU_DAILY_ENG_XML.getUrl() + date.format(ofPattern("dd/MM/yyyy")))
        );
    }
}
