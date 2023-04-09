package com.example.just_project.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppMsgErrors {

    public static final String CONTENT_SERIALIZATION_FAILED_RU = "Не удалось сериализовать контент: %s";
    public static final String FAILED_TO_READ_CONTENT = "Failed to read content";
    public static final String AN_UNEXPECTED_ERROR_OCCURRED_RU = "Возникла непредвиденная ошибка";
    public static final String AN_UNEXPECTED_ERROR_OCCURRED_EN = "An unforeseen mistake arose";
    public static final String FAILED_TO_CONNECT_TO_URL = "Failed to connect to: %s";
    public static final String ERROR_ACCESSING_TO = "Ошибка доступа к: %s";
    public static final String FAILED_TO_GENERATE_REPORT = "Failed to generate report";
}
