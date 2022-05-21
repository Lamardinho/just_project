package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.exchangerate.dto.RubleRateDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsRate;
import com.example.just_project.exchangerate.services.contract.ExchangeRateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateController {

    @NonNull
    private final ExchangeRateService rateService;

    @GetMapping("/ruble/usd_euro")
    public RubleRateDto getUsdAndEuroRateByRuble() {
        return rateService.getUsdAndEuroRateByRuble();
    }

    @GetMapping("/ruble/all")
    public Map<?, ?> getAllRatesByRuble() {                                                                             //NOSONAR
        return rateService.getAllRatesByRuble();
    }

    @GetMapping("/ruble/rate")
    public ExchangeRateDtoWhereRateIsRate getRate() {
        return rateService.getRate();
    }
}
