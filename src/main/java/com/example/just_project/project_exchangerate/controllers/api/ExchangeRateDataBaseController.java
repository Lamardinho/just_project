package com.example.just_project.project_exchangerate.controllers.api;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.common.util.ContractResult;
import com.example.just_project.project_exchangerate.services.ExchangeRateDataBaseService;
import com.example.just_project.project_exchangerate.util.ExchangeRateMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Курсы валют. DB", description = "Получение курса валют из online источников, запись в БД и чтение")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/db", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateDataBaseController {

    @NonNull
    private final ExchangeRateDataBaseService dataBaseService;

    /*@Operation(
            summary = "Обновить рейтинги сегодняшнего дня с сайта " + AppConstants.RUBLE_CBR_DAILY_RU_URL,
            description = "Сохраняет сегодняшние рейтинги в БД, если сегодняшний день уже есть в БД, то просто обновляет его"
    )
    @TrackExecutionTime
    @PutMapping("/ruble/update/today")
    public ContractResult<Boolean> createOrUpdateFromJson() {
        dataBaseService.createOrUpdateFromJson(AppConstants.RUBLE_CBR_DAILY_RU_URL, ERate.RUB);
        return new ContractResult<>(true).setMessage(ExchangeRateMessages.RATINGS_HAVE_BEEN_UPDATED);
    }*/

    @Operation(
            summary = "Обновить рейтинги сегодняшнего дня c cbr.ru",
            description = "Сохраняет сегодняшние рейтинги в БД, если сегодняшний день уже есть в БД, то просто обновляет его"
    )
    @TrackExecutionTime
    @PutMapping("/ruble/update/today/cbr")
    public ContractResult<Boolean> createOrUpdateFromCbrXml() {
        dataBaseService.createOrUpdateFromCbrXml();
        return new ContractResult<>(true).setMessage(ExchangeRateMessages.RATINGS_HAVE_BEEN_UPDATED);
    }

    /*@Operation(summary = "Загрузить последние рейтинги")
    @ApiPageable
    @TrackExecutionTime
    @GetMapping("/ruble/all")
    public ContractResult<List<CurrencyRateByUsdAndEuroDto>> findAll(
            @ApiIgnore
            @PageableDefault(size = 30, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        val result = dataBaseService.getAllCurrencyRateByUsdAndEuroDtoList(pageable);
        return new ContractResult<>(result).setMessage("size: " + result.size());
    }*/
}
