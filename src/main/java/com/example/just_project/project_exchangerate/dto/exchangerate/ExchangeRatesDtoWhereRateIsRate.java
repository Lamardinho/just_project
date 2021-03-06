package com.example.just_project.project_exchangerate.dto.exchangerate;

import com.example.just_project.project_exchangerate.dto.Rates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * DTO для получения данных с сайта: <a href="https://www.cbr-xml-daily.ru/latest.js">cbr-xml-daily.ru</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ExchangeRatesDtoWhereRateIsRate extends AExchangeRates {

    private Rates rates;
}
