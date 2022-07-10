package com.example.just_project.project_exchangerate.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeErrors {

    public static final String DATA_SOURCE_NOT_FOUND = "В БД нет такого ресурса: %s";
    public static final String FAILED_TO_UPDATE_RATING = "failed to update rating";
}
