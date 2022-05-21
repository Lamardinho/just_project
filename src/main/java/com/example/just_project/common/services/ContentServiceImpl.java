package com.example.just_project.common.services;

import com.example.just_project.common.services.contract.BufferedReaderService;
import com.example.just_project.common.services.contract.ConnectService;
import com.example.just_project.common.services.contract.ContentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    @NonNull
    private final ConnectService connectService;
    @NonNull
    private final BufferedReaderService bufferedReaderService;

    @Override
    @SneakyThrows
    public String getContentFromUrl(String url, Integer connectTimeout, Integer readTimeout) {
        try (val stream = connectService
                .getConnection(url, connectTimeout, readTimeout)
                .getInputStream()
        ) {
            return bufferedReaderService.readAll(
                    new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
            );
        }
    }
}
