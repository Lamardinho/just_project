package com.example.just_project.exchangerate.services;

import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.services.contract.BufferedReaderService;
import com.example.just_project.common.services.contract.ConnectService;
import com.example.just_project.exchangerate.dto.ExchangeRateDto;
import com.example.just_project.exchangerate.dto.RubleRateDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static com.example.just_project.util.CurrencyHelper.currencyToRuble;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExchangeRateService {

    @NonNull
    private final ObjectMapperService objMapService;
    @NonNull
    private final ConnectService connectService;
    @NonNull
    private final BufferedReaderService bufferedReaderService;

    private static final int CONNECTION_TIMEOUT = 8_000;
    private static final String RUBLE_URL = "https://www.cbr-xml-daily.ru/latest.js";

    public RubleRateDto getRubleRate() {
        val exchangeRateDto = objMapService.readValue(getStringFromUrl(RUBLE_URL), ExchangeRateDto.class);

        return new RubleRateDto(
                exchangeRateDto.getDisclaimer(),
                exchangeRateDto.getDate(),
                exchangeRateDto.getBase(),
                currencyToRuble(exchangeRateDto.getRates().getUsd()),
                currencyToRuble(exchangeRateDto.getRates().getEur())
        );
    }

    @SneakyThrows
    private String getStringFromUrl(String url) {
        try (val stream = connectService.getStream(url, CONNECTION_TIMEOUT, CONNECTION_TIMEOUT)) {
            return bufferedReaderService.readAll(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));
        }
    }
}
