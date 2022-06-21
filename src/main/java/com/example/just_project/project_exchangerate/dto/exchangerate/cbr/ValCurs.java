package com.example.just_project.project_exchangerate.dto.exchangerate.cbr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO для получения данных с сайта: <a href="https://www.cbr.ru/scripts/XML_daily_eng.asp?date_req=">...</a>
 */
@Schema(description = "Exchange Rates/Курс валют")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "ValCurs")
public class ValCurs {

    @Schema(description = "Currencies/Валюты")
    @JsonProperty("Valute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Valute> currencies = new ArrayList<>();

    @Schema(description = "Course date/Дата курса")
    @JsonProperty("Date")
    private String date;

    @Schema(description = "Page name/Имя страницы")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Request time/Время запроса")
    private Instant time = Instant.now();
}
