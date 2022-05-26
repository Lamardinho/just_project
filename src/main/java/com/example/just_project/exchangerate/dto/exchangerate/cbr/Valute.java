package com.example.just_project.exchangerate.dto.exchangerate.cbr;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    public String id;

    @JsonProperty("NumCode")
    public int numCode = 0;

    @JsonProperty("CharCode")
    public String charCode;

    @JsonProperty("Nominal")
    public int nominal;

    @JsonProperty("Name")
    public String name;

    @JsonProperty("Value")
    public String value;
}
