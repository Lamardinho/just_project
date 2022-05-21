package com.example.just_project.common.services;

import com.example.just_project.common.services.contract.ConnectService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Log4j2
@Component
@RequiredArgsConstructor
public class ConnectServiceImpl implements ConnectService {

    @Override
    @SneakyThrows
    public InputStream getStream(
            @NonNull String url,
            @NonNull Integer connectTimeout,
            @NonNull Integer readTimeout
    ) {
        val connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);    // TODO: убрать? //NOSONAR

        return connection.getInputStream();
    }
}
