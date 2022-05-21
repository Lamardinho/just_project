package com.example.just_project.exchangerate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RubleRateDto {

    private String disclaimer;
    private LocalDate date;
    /**
     * Валюта
     */
    private String base;
    private String usd = "?";
    private String euro = "?";
}
