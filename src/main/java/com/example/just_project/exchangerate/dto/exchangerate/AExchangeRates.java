package com.example.just_project.exchangerate.dto.exchangerate;

import com.example.just_project.exchangerate.enums.ERate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public abstract class AExchangeRates {

    @Schema(description = "Source/Источник")
    private String disclaimer;

    @Schema(description = "Rating date/Дата рейтинга")
    private LocalDate date;

    @Schema(description = "Request time/Время запроса")
    private Instant timeRequest = Instant.now();

    @Schema(description = "Currency/Валюта")
    private ERate base;
}
