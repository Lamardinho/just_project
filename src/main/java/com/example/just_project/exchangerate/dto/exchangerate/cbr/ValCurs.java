package com.example.just_project.exchangerate.dto.exchangerate.cbr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO для получения данных с сайта: <a href="https://www.cbr-xml-daily.ru/daily_json.js">cbr-xml-daily.ru</a>
 * <p>
 * Info: <a href="https://www.cbr-xml-daily.ru/#howto">https://www.cbr-xml-daily.ru/#howto</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "ValCurs")
public class ValCurs {

    @JsonProperty("Valute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Valute> currencies = new ArrayList<>();

    @Schema(description = "Course date/Дата курса")
    @JsonProperty("Date")
    private String date;

    @JsonProperty("name")
    private String name;

    private LocalDate time = LocalDate.now();
}
