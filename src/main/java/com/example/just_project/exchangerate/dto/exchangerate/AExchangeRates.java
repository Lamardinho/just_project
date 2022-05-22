package com.example.just_project.exchangerate.dto.exchangerate;

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
    private String base;
}
