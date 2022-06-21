package com.example.just_project.common.services;

import com.example.just_project.common.util.AppException;
import com.example.just_project.common.util.AppMsgErrors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static java.lang.String.format;

@Log4j2
@RequiredArgsConstructor
public class XmlMapperService {

    private final XmlMapper xmlMapper;
    private final RestTemplate restTemplate;

    @SneakyThrows
    public <T> T readXml(URL url, Class<T> valueType) {
        return xmlMapper.readValue(url, valueType);
    }

    public <T> T readXml(String url, Class<T> valueType) {
        val content = getContentFromUrl(url);
        return toDto(content, valueType);
    }

    private <T> T toDto(String content, Class<T> valueType) {
        try {
            return xmlMapper.readValue(content, valueType);
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage());
            throw new AppException("Parsing KP XML exception:\n" + exception.getMessage());
        }
    }

    private String getContentFromUrl(String url) {
        var content = "";
        try {
            content = restTemplate.getForObject(url, String.class);
            if (StringUtils.isBlank(content)) {
                throw new AppException(AppMsgErrors.FAILED_TO_READ_CONTENT);
            }
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            log.error(exception.getMessage());
            throw new AppException(format(AppMsgErrors.ERROR_ACCESSING_TO, url));
        }
        return content;
    }
}
