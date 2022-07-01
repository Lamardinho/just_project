package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.services.ContentService;
import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.services.XmlMapperService;
import com.example.just_project.project_exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import com.example.just_project.project_exchangerate.dtomappers.ExchangeRateMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Cacheable(cacheNames = "getCurrencyRateByUsdAndEuro")
    public CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro() {
        val rate = objMapService.readValue(
                contentService.getContentFromUrl(CBR_NOT_OFFICIAL_XML_DAILY_RU_LATEST_JSON.getUrl()),
                ExchangeRatesDtoWhereRateIsMapStr.class
        );
        return exchangeRateMapper.dtoWhereRateIsMapStrToCurrencyRateByUsdAndEuroDto(rate);
    }

    @Cacheable(cacheNames = "getRubleRateJsonFromCbrUrlXml")
    @SneakyThrows
    public ValCurs getRubleRateJsonFromCbrUrlXml(LocalDate date) {
        return xmlMapperService.readXml(
                new URL(CBR_RU_DAILY_ENG_XML.getUrl() + date.format(ofPattern("dd/MM/yyyy"))),
                ValCurs.class
        );
    }
}
