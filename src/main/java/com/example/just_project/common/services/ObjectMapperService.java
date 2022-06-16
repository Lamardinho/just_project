package com.example.just_project.common.services;

import com.example.just_project.util.AppException;
import com.example.just_project.util.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class ObjectMapperService {

    @NonNull
    private final ObjectMapper objectMapper;

    public <T> T readValue(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        } catch (Exception e) {
            log.error(String.format(Msg.CONTENT_SERIALIZATION_FAILED_RU, content));
            throw new AppException(e.getMessage());
        }
    }

    public <T> T readValue(URL content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        } catch (Exception e) {
            log.error(String.format(Msg.CONTENT_SERIALIZATION_FAILED_RU, content));
            throw new AppException(e.getMessage());
        }
    }

    public Map<?, ?> readValueToMap(String content) { //NOSONAR
        try {
            return objectMapper.readValue(content, Map.class);
        } catch (Exception e) {
            log.error(String.format(Msg.CONTENT_SERIALIZATION_FAILED_RU, content));
            throw new AppException(e.getMessage());
        }
    }

    public String writeValueAsString(Object value) { //NOSONAR
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            log.error(String.format(Msg.CONTENT_SERIALIZATION_FAILED_RU, value.toString()));
            throw new AppException(e.getMessage());
        }
    }
}
