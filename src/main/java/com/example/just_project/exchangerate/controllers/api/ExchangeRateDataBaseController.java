package com.example.just_project.exchangerate.controllers.api;

import com.example.just_project.config.ApiPageable;
import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.services.ExchangeRateDataBaseService;
import com.example.just_project.util.ContractResult;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static com.example.just_project.util.AppMsgErrors.RATINGS_HAVE_BEEN_UPDATED;

@Tag(name = "Курсы валют. DB", description = "Получение курса валют из online источников, запись в БД и чтение")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/db", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeRateDataBaseController {

    @NonNull
    private final ExchangeRateDataBaseService dataBaseService;

    @Operation(
            summary = "Обновить рейтинги",
            description = "Сохраняет сегодняшние рейтинги в БД, если сегодняшний день уже есть в БД, то просто обновляет его"
    )
    @GetMapping("/ruble/update")
    public ContractResult<Boolean> getUsdAndEuroRateByRuble() {
        dataBaseService.createOrUpdate();
        return new ContractResult<>(true).setMessage(RATINGS_HAVE_BEEN_UPDATED);
    }

    @GetMapping("/ruble/all")
    @ApiPageable
    @Operation(summary = "Загрузить последние рейтинги")
    public ContractResult<List<CurrencyRateByUsdAndEuroDto>> findAll(
            @ApiIgnore
            @PageableDefault(size = 30, sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        val result = dataBaseService.getAllCurrencyRateByUsdAndEuroDtoList(pageable);
        return new ContractResult<>(result).setMessage("size: " + result.size());
    }
}
