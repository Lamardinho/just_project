package com.example.just_project.common.services.contract;

import java.util.Map;

public interface ObjectMapperService {
    <T> T readValue(String content, Class<T> clazz);

    Map<?, ?> readValueToMap(String content); //NOSONAR

    String writeValueAsString(Object value);
}
