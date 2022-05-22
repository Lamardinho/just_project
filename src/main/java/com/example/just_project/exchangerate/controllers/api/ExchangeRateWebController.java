package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.common.services.contract.ContentService;
import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.services.contract.ExchangeRateWebService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.just_project.exchangerate.util.AppConstants.RUBLE_CBR_DAILY_RU_URL;

/**
 * Для работы с курсом валют с вебсайтов
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/web", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateWebController {

    @NonNull
    private final ExchangeRateWebService exchangeRateWebService;
    @NonNull
    private final ContentService contentService;

    @GetMapping("/ruble/basic")
    public CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro() {
        return exchangeRateWebService.getCurrencyRateByUsdAndEuro();
    }

    @GetMapping("/ruble/all")
    public Map<?, ?> getRubleRate() { //NOSONAR
        return contentService.getJsonFromUrl(RUBLE_CBR_DAILY_RU_URL);
    }
}
