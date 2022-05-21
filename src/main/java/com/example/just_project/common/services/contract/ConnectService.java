package com.example.just_project.common.services.contract;

import lombok.NonNull;

import java.io.InputStream;

public interface ConnectService {

    InputStream getStream(
            @NonNull String url,
            @NonNull Integer connectTimeout,
            @NonNull Integer readTimeout
    );
}
