package com.example.just_project.common.services;

import com.example.just_project.common.services.contract.BufferedReaderService;
import com.example.just_project.common.services.contract.ConnectionService;
import com.example.just_project.common.services.contract.ContentService;
import com.example.just_project.common.services.contract.ObjectMapperService;
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
public class ContentServiceImpl implements ContentService {

    @NonNull
    private final ConnectionService connectionService;
    @NonNull
    private final BufferedReaderService bufferedReaderService;
    @NonNull
    private final ObjectMapperService objectMapperService;

    @Override
    @SneakyThrows
    public String getContentFromUrl(String url, Integer connectTimeout, Integer readTimeout) {
        try (val stream = connectionService
                .getConnection(url, connectTimeout, readTimeout)
                .getInputStream()
        ) {
            return bufferedReaderService.readAll(
                    new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
            );
        }
    }

    @Override
    public Map<?, ?> getJsonFromUrl(String url) { //NOSONAR
        val content = getContentFromUrl(url, 8000, 8000);
        return objectMapperService.readValueToMap(content);
    }
}
