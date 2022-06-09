package com.example.just_project.exchangerate.dto;

import com.example.just_project.exchangerate.dto.exchangerate.AExchangeRates;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "USD/Доллар")
    private String usd = "?";

    @Schema(description = "EUR/Евро")
    private String euro = "?";
}
