package com.example.just_project.exchangerate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * DTO для получения данных с сайта: <a href="https://www.cbr-xml-daily.ru/latest.js">cbr-xml-daily.ru</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ExchangeRateDto {

    /**
     * Источник
     */
    private String disclaimer;

    private LocalDate date;

    /**
     * Валюта
     */
    private String base;

    private Rates rates;
}
