package com.example.just_project.exchangerate.dtomappers;

import com.example.just_project.common.services.contract.ObjectMapperService;
import com.example.just_project.exchangerate.dto.CurrencyRateByUsdAndEuroDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRatesDtoWhereRateIsMapStr;
import com.example.just_project.exchangerate.enums.ERate;
import com.example.just_project.exchangerate.model.ExchangeRate;
import com.example.just_project.util.CurrencyHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        //uses = {},
        imports = {CurrencyHelper.class, ERate.class}
)
public abstract class ExchangeRateMapper {

    @Autowired
    protected ObjectMapperService objectMapperService;

    @Mapping(target = "usd", expression = "java(CurrencyHelper.calculateToRub(rate.getRates().get(ERate.USD.name())))")
    @Mapping(target = "euro", expression = "java(CurrencyHelper.calculateToRub(rate.getRates().get(ERate.EUR.name())))")
    public abstract CurrencyRateByUsdAndEuroDto dtoWhereRateIsMapStrToCurrencyRateByUsdAndEuroDto(ExchangeRatesDtoWhereRateIsMapStr rate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rates", expression = "java(objectMapperService.writeValueAsString(dto.getRates()))")
    public abstract ExchangeRate toExchangeRate(ExchangeRatesDtoWhereRateIsMapStr dto, @MappingTarget ExchangeRate rate);

    @Mapping(target = "usd", ignore = true)
    @Mapping(target = "euro", ignore = true)
    public abstract CurrencyRateByUsdAndEuroDto toCurrencyRateByUsdAndEuroDto(ExchangeRate rate);
}
