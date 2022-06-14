package com.example.just_project.common.services.contract;

import java.net.URL;

public interface XmlMapperService {

    <T> T readXml(String url, Class<T> valueType);

    <T> T readXml(URL url, Class<T> valueType);
}
