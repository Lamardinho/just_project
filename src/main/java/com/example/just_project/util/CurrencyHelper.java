package com.example.just_project.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyHelper {

    public static String currencyTo(double d, String currency) {
        return Math.ceil(1.0 / d) + " " + currency;
    }

    public static String currencyToRuble(double d) {
        return Math.ceil(1.0 / d) + " rub";
    }
}
