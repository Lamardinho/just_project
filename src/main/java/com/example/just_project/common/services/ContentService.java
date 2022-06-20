package com.example.just_project.common.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class ContentService {

    @NonNull
    private final ConnectionService connectionService;
    @NonNull
    private final BufferedReaderService bufferedReaderService;
    @NonNull
    private final ObjectMapperService objectMapperService;

    public String getContentFromUrl(String url) {
        return getContentFromUrl(url, 5000, 5000);
    }

    @SneakyThrows
    public String getContentFromUrl(String url, Integer connectTimeout, Integer readTimeout) {
        try (
                val stream = connectionService
                        .getConnection(url, connectTimeout, readTimeout)
                        .getInputStream()
        ) {
            return bufferedReaderService.readAll(
                    new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
            );
        }
    }

    public Map<?, ?> getJsonFromUrl(String url) { //NOSONAR
        val content = getContentFromUrl(url, 5000, 5000);
        return objectMapperService.readValueToMap(content);
    }
}
