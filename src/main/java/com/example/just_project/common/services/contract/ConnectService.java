package com.example.just_project.common.services.contract;

import lombok.NonNull;

import java.net.HttpURLConnection;

public interface ConnectService {

    HttpURLConnection getConnection(
            @NonNull String url,
            @NonNull Integer connectTimeout,
            @NonNull Integer readTimeout
    );
}
