package com.example.just_project.exchangerate.dtomappers;

import com.example.just_project.exchangerate.dto.BasicCurrenciesRateDto;
import com.example.just_project.exchangerate.dto.exchangerate.ExchangeRateDtoWhereRateIsMapStr;
import com.example.just_project.exchangerate.enums.ERate;
import com.example.just_project.util.CurrencyHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        //uses = {},
        imports = {CurrencyHelper.class, ERate.class}
)
public interface ExchangeRateMapper {

    @Mapping(target = "usd", expression = "java(CurrencyHelper.calculateToRub(rate.getRates().get(ERate.USD.name())))")
    @Mapping(target = "euro", expression = "java(CurrencyHelper.calculateToRub(rate.getRates().get(ERate.EUR.name())))")
    BasicCurrenciesRateDto dtoWhereRateIsMapStrToBasicCurrenciesRateDto(ExchangeRateDtoWhereRateIsMapStr rate);

}
