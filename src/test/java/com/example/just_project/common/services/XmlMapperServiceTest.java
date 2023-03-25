package com.example.just_project.common.services;

import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.just_project.project_exchangerate.enums.ESource.CBR_RU_DAILY_ENG_XML;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class XmlMapperServiceTest {

    private final XmlMapperService xmlMapperService = new XmlMapperService(new XmlMapper(), new RestTemplate());

    @Test
    void readXml_urlUrl() throws MalformedURLException {
        final var valCurs = xmlMapperService.readXml(new URL(CBR_RU_DAILY_ENG_XML.getUrl()), ValCurs.class);

        assertNotNull(valCurs);
    }

    @Test
    void readXml_stringUrl() {
        final var valCurs = xmlMapperService.readXml(CBR_RU_DAILY_ENG_XML.getUrl(), ValCurs.class);

        assertNotNull(valCurs);
    }
}
