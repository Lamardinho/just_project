package com.example.just_project.project_exchangerate.dto.exchangerate.cbr;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Valute {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("NumCode")
    private int numCode = 0;

    @Schema(description = "Currency abbreviation/Аббревиатура валюты")
    @JsonProperty("CharCode")
    private String charCode;

    @JsonProperty("Nominal")
    private int nominal;

    @Schema(description = "Currency decryption/Расшифровка валюты")
    @JsonProperty("Name")
    private String name;

    @Schema(description = "Price/Цена")
    @JsonProperty("Value")
    private String value;
}
