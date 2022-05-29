package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.services.contract.ExchangeRateDataBaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Курсы валют. DB", description = "Получение курса валют из online источников, запись в БД и чтение")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/db", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateDataBaseController {

    @NonNull
    private final ExchangeRateDataBaseService dataBaseService;

    @GetMapping("/ruble/update")
    public void getUsdAndEuroRateByRuble() {
        dataBaseService.createOrUpdate();
    }

    @GetMapping("/ruble/all")
    public List<CurrencyRateByUsdAndEuroDto> findAll() {
        return dataBaseService.getAllCurrencyRateByUsdAndEuroDtoList();
    }
}
