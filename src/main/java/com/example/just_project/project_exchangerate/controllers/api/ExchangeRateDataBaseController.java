package com.example.just_project.project_exchangerate.controllers.api;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.common.util.ContractResult;
import com.example.just_project.config.ApiPageable;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRateDto;
import com.example.just_project.project_exchangerate.services.ExchangeRateDataBaseService;
import com.example.just_project.project_exchangerate.util.ExchangeRateMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static com.example.just_project.project_exchangerate.enums.ERate.RUB;
import static com.example.just_project.project_exchangerate.enums.ESource.CBR_RU_DAILY_ENG_XML;

@Tag(name = "Курсы валют. DB", description = "Получение курса валют из online источников, запись в БД и чтение")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/db", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateDataBaseController {

    @NonNull
    private final ExchangeRateDataBaseService dataBaseService;

    @Operation(
            summary = "Обновить рейтинги сегодняшнего дня c cbr.ru",
            description = "Сохраняет сегодняшние рейтинги в БД, если сегодняшний день уже есть в БД, то просто обновляет его"
    )
    @TrackExecutionTime
    @PutMapping("/ruble/cbr/update/today")
    public ContractResult<Boolean> createOrUpdateFromCbrXml() {
        dataBaseService.createOrUpdateRubleRateFromCbrXml(CBR_RU_DAILY_ENG_XML);
        return new ContractResult<>(true).setMessage(ExchangeRateMessages.RATINGS_HAVE_BEEN_UPDATED);
    }

    @Operation(summary = "Загрузить все рейтинги")
    @ApiPageable
    @TrackExecutionTime
    @GetMapping("/ruble/cbr/all")
    public ContractResult<List<ExchangeRateDto>> findAll(
            @ApiIgnore
            @PageableDefault(size = 30, sort = {"dateRating"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        val result = dataBaseService.findAllExchangeRateDtoListAndFilterByUsdAndEurCacheable(
                RUB, CBR_RU_DAILY_ENG_XML, pageable
        );
        return new ContractResult<>(result).setMessage("size: " + result.size());
    }

    @Operation(summary = "Загрузить рейтинги за последние 30 дней", description = "использует кешированный метод")
    @ApiPageable
    @TrackExecutionTime
    @GetMapping("/ruble/cbr/last-30-days")
    public ContractResult<List<ExchangeRateDto>> findLast30Days(boolean sortDesc) {
        val result = dataBaseService.findLast30ExchangeRateDtoListAndFilterByUsdAndEurCacheable(
                RUB, CBR_RU_DAILY_ENG_XML, sortDesc
        );
        return new ContractResult<>(result).setMessage("size: " + result.size());
    }
}
