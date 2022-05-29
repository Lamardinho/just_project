package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.common.services.contract.XmlMapperService;
import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.dto.exchangerate.cbr.ValCurs;
import com.example.just_project.exchangerate.services.contract.ExchangeRateWebService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static com.example.just_project.exchangerate.util.AppConstants.CBR_XML_DAILY_ENG_BY_DATE_URL;
import static java.time.format.DateTimeFormatter.ofPattern;

@Tag(name = "Курсы валют. Online", description = "Просмотр курсов валют из online источников")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/web", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateWebController {

    @NonNull
    private final ExchangeRateWebService exchangeRateWebService;
    @NonNull
    private final XmlMapperService xmlMapperService;

    @Operation(
            summary = "Получение котировок на заданный день с www.cbr.ru",
            description = "Укажите дату в формате day-month-year (не раньше 01-01-1992)," +
                    " или оставьте пустым для получения самых актуальных рейтингов"
    )
    @PostMapping("/cbr/ruble/json/all")
    public ValCurs getRubleRateJsonFromCbrUrlXml(
            @ApiParam(example = "28-05-2022")
            @DateTimeFormat(pattern = "dd-MM-yyyy")
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")
            LocalDate date
    ) throws MalformedURLException {
        return xmlMapperService.readXml(
                new URL(CBR_XML_DAILY_ENG_BY_DATE_URL + date.format(ofPattern("dd-MM-yyyy"))),
                ValCurs.class
        );
    }

    @Operation(summary = "Получить рейтинг рубля на сегодняшний день")
    @GetMapping("/cbr/ruble/basic")
    public CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro() {
        return exchangeRateWebService.getCurrencyRateByUsdAndEuro();
    }
}
