package com.example.just_project.exchangerate.dto;

import com.example.just_project.exchangerate.dto.exchangerate.AExchangeRates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CurrencyRateByUsdAndEuroDto extends AExchangeRates {

    private String usd = "?";

    private String euro = "?";
}
