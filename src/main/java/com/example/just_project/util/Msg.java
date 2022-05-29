package com.example.just_project.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Msg {

    public static final String CONTENT_SERIALIZATION_FAILED_RU = "Не удалось сериализовать контент: %s";
    public static final String FAILED_TO_READ_CONTENT = "Failed to read content";
    public static final String AN_UNEXPECTED_ERROR_OCCURRED_RU = "Возникла непредвиденная ошибка";
    public static final String AN_UNEXPECTED_ERROR_OCCURRED_EN = "An unforeseen mistake arose";
    public static final String RATINGS_HAVE_BEEN_UPDATED = "Рейтинги обновлены";
}
