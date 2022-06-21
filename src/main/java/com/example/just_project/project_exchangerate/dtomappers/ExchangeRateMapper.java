package com.example.just_project.project_exchangerate.dtomappers;

import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.project_exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.project_exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.project_exchangerate.enums.ERate;
import com.example.just_project.project_exchangerate.util.CurrencyCalculateHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        //uses = {},
        imports = {CurrencyCalculateHelper.class, ERate.class}
)
public abstract class ExchangeRateMapper {

    @Autowired
    protected ObjectMapperService objectMapperService;

    /*@Mapping(target = "id", ignore = true)
    @Mapping(target = "rates", expression = "java(objectMapperService.writeValueAsString(dto.getRates()))")
    public abstract ExchangeRate toExchangeRate(ExchangeRatesDtoWhereRateIsMapStr dto, @MappingTarget ExchangeRate rate);*/

    /*@Mapping(target = "usd", ignore = true)
    @Mapping(target = "euro", ignore = true)
    public abstract CurrencyRateByUsdAndEuroDto toCurrencyRateByUsdAndEuroDto(ExchangeRate rate);*/

    @Mapping(target = "usd", expression = "java(CurrencyCalculateHelper.calculateToRub(rate.getRates().get(ERate.USD.name())))")
    @Mapping(target = "euro", expression = "java(CurrencyCalculateHelper.calculateToRub(rate.getRates().get(ERate.EUR.name())))")
    public abstract CurrencyRateByUsdAndEuroDto dtoWhereRateIsMapStrToCurrencyRateByUsdAndEuroDto(ExchangeRatesDtoWhereRateIsMapStr rate);
}
