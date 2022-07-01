package com.example.just_project.project_exchangerate.controllers.api;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.project_exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import com.example.just_project.project_exchangerate.services.ExchangeRateWebService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "Курсы валют. Online", description = "Просмотр курсов валют из online источников")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/web", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateWebController {

    @NonNull
    private final ExchangeRateWebService exchangeRateWebService;

    @Operation(
            summary = "Получение котировок на заданный день с www.cbr.ru",
            description = "Укажите дату в формате day-month-year (не раньше 01-07-1992)," +
                    " или оставьте пустым для получения самых актуальных рейтингов"
    )
    @GetMapping("/cbr/ruble/json/all")
    @TrackExecutionTime
    public ValCurs getRubleRateJsonFromCbrUrlXml(
            @ApiParam(example = "28/05/2022")
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")
            LocalDate date
    ) {
        return exchangeRateWebService.getRubleRateJsonFromCbrUrlXml(date);
    }

    @Operation(summary = "Получить рейтинг рубля на сегодняшний день")
    @TrackExecutionTime
    @GetMapping("/cbr/ruble/basic")
    public CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro() {
        return exchangeRateWebService.getCurrencyRateByUsdAndEuro();
    }
}
