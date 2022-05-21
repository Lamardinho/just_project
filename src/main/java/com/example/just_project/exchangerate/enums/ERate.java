package com.example.just_project.exchangerate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERate {

    RUB("RUB"),
    EURO("EURO"),
    USD("USD");

    private final String name;
}
