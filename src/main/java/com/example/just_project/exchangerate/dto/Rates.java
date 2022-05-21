package com.example.just_project.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Rates {

    @JsonProperty("USD")
    private Double usd;

    @JsonProperty("EUR")
    private Double eur;
}
