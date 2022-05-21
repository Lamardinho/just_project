package com.example.just_project.controllers.api;

import com.example.just_project.exchangerate.dto.RubleRateDto;
import com.example.just_project.exchangerate.services.ExchangeRateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateController {

    @NonNull
    private final ExchangeRateService rateService;

    @GetMapping("/ruble")
    public RubleRateDto getRubleRate() {
        return rateService.getRubleRate();
    }
}
