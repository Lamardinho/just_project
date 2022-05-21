package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.exchangerate.dto.BasicCurrenciesRateDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsRate;
import com.example.just_project.exchangerate.services.contract.ExchangeRateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Для работы с курсом валют с вебсайтов
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/web", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateWebController {

    @NonNull
    private final ExchangeRateService rateService;

    @GetMapping("/ruble/basic")
    public BasicCurrenciesRateDto getUsdAndEuroRateByRuble() {
        return rateService.getBasicRatesByRuble();
    }

    @GetMapping("/ruble/all/v1")
    public Map<?, ?> getAllRatesByRuble() { //NOSONAR
        return rateService.getAllRatesByRuble();
    }

    @GetMapping("/ruble/all/v2")
    public ExchangeRateDtoWhereRateIsRate getRate() {
        return rateService.getRatesByRuble();
    }
}
