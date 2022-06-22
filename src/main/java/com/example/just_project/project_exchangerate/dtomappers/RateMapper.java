package com.example.just_project.project_exchangerate.dtomappers;

import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.Valute;
import com.example.just_project.project_exchangerate.model.exchangerate.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface RateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "value", expression = "java(Double.valueOf(valute.getValue().replace(',','.')))")
    Rate toEntity(Valute valute);
}
