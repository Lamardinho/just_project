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

    /**
     * Валюта
     */
    private String base;

    private Instant updated = Instant.now();
}
