package com.example.just_project.common.services.contract;

public interface ContentService {

    String getContentFromUrl(String url, Integer connectTimeout, Integer readTimeout);
}
