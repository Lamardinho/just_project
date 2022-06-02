package com.example.just_project.config;

import com.example.just_project.common.services.XmlMapperServiceImpl;
import com.example.just_project.common.services.contract.XmlMapperService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ContentServiceConfig {

    private XmlMapper xmlMapper() {
        return new XmlMapper();
    }

    @Bean
    public XmlMapperService defaultContentService() {
        return new XmlMapperServiceImpl(xmlMapper(), restTemplate());
    }

    private RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(
                        HttpClientBuilder.create().build()))
                .build();
    }
}
