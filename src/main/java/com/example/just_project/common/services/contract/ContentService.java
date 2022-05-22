package com.example.just_project.common.services.contract;

import java.util.Map;

public interface ContentService {

    String getContentFromUrl(String url, Integer connectTimeout, Integer readTimeout);

    Map<?, ?> getJsonFromUrl(String url);//NOSONAR
}
