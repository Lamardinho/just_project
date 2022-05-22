package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
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
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/ruble/basic")
    public CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro() {
        return exchangeRateService.getCurrencyRateByUsdAndEuro();
    }

    @GetMapping("/ruble/all/v1")
    public Map<?, ?> getAllRatesByCurrency() { //NOSONAR
        return exchangeRateService.getAllRatesByCurrency();
    }

    /*@GetMapping("/ruble/all/v2")
    public ExchangeRatesDtoWhereRateIsRate getRatesByCurrency() {
        return exchangeRateService.getRatesByCurrency();
    }*/
}
