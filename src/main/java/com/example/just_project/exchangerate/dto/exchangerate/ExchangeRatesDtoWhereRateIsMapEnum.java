package com.example.just_project.exchangerate.dto.exchangerate;

import com.example.just_project.exchangerate.enums.ERate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * DTO для получения данных с сайта: <a href="https://www.cbr-xml-daily.ru/latest.js">cbr-xml-daily.ru</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ExchangeRatesDtoWhereRateIsMapEnum extends AExchangeRates {

    @Schema(description = "Rates/Ставки")
    private Map<ERate, Double> rates;
}
