package com.example.just_project.project_exchangerate.dto.exchangerate;

import com.example.just_project.project_exchangerate.enums.ERate;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExchangeRateDto implements Serializable {

    private String dataSourceUrl;

    private LocalDate dateRating;

    private ERate currency;

    private List<RateDto> rates = new ArrayList<>();
}
