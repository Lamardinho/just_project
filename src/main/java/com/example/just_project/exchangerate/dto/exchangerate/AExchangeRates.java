package com.example.just_project.exchangerate.dto.exchangerate;

import com.example.just_project.exchangerate.enums.ERate;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public abstract class AExchangeRates {

    /**
     * Источник
     */
    private String disclaimer;

    private LocalDate date;

    private Instant timeRequest = Instant.now();

    /**
     * Валюта
     */
    private ERate base;
}
