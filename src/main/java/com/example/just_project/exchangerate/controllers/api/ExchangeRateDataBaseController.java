package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.services.contract.ExchangeRateDataBaseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Для работы с базой данных курсом валют
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/db", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateDataBaseController {

    @NonNull
    private final ExchangeRateDataBaseService dataBaseService;

    @GetMapping("/ruble/update")
    public void getUsdAndEuroRateByRuble() {
        dataBaseService.create();
    }

    @GetMapping("/ruble/test")
    public CurrencyRateByUsdAndEuroDto test() {
        return dataBaseService.findByIds();
    }
}
