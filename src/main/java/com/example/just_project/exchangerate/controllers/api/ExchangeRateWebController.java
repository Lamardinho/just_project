package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.common.services.contract.ContentService;
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
import java.util.Map;

import static com.example.just_project.exchangerate.util.AppConstants.CBR_XML_DAILY_ENG_BY_DATE_URL;
import static com.example.just_project.exchangerate.util.AppConstants.RUBLE_CBR_DAILY_RU_URL;
import static java.time.format.DateTimeFormatter.ofPattern;

@Tag(name = "Курсы валют", description = "Получение курса валют из online источников")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/web", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateWebController {

    @NonNull
    private final ExchangeRateWebService exchangeRateWebService;
    @NonNull
    private final ContentService contentService;
    @NonNull
    private final XmlMapperService xmlMapperService;

    @GetMapping("/cbr/ruble/basic")
    public CurrencyRateByUsdAndEuroDto getCurrencyRateByUsdAndEuro() {
        return exchangeRateWebService.getCurrencyRateByUsdAndEuro();
    }

    @GetMapping("/cbr/ruble/all")
    public Map<?, ?> getRubleRate() { //NOSONAR
        return contentService.getJsonFromUrl(RUBLE_CBR_DAILY_RU_URL);
    }

    @Operation(
            summary = "Получение котировок на заданный день с www.cbr.ru",
            description = "укажите дату в формате day-month-year, или оставьте без изменений для получения самых актуальных рейтингов"
    )
    @GetMapping("/cbr/ruble/xml/all/{date}")
    public ValCurs getRubleRateFromCbrUrlXml(
            @PathVariable(value = "date")
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")
            @DateTimeFormat(pattern = "dd-MM-yyyy")
            @ApiParam(example = "28-05-2022")
            LocalDate date
    ) throws MalformedURLException {
        return xmlMapperService.readXml(
                new URL(CBR_XML_DAILY_ENG_BY_DATE_URL + date.format(ofPattern("dd-MM-yyyy"))),
                ValCurs.class
        );
    }
}
