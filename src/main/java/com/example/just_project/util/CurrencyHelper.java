package com.example.just_project.util;

import com.example.just_project.exchangerate.enums.ERate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Locale;

import static com.example.just_project.exchangerate.enums.ERate.RUB;
import static com.example.just_project.exchangerate.enums.ERate.USD;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyHelper {

    public static String calculateTo(double sum, @NonNull String currency) {
        return calculate(sum) + " " + currency;
    }

    public static String calculateToRub(double sum) {
        return calculate(sum) + " " + toLower(RUB);
    }

    public static String calculateToUsd(double sum) {
        return calculate(sum) + " " + toLower(USD);
    }

    private static double calculate(double sum) {
        return Math.ceil(1.0 / sum);
    }

    private static String toLower(ERate rate) {
        return rate.name().toLowerCase(Locale.ROOT);
    }
}
