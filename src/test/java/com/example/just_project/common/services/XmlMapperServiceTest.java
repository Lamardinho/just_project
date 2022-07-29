package com.example.just_project.common.services;

import com.example.just_project.project_exchangerate.dto.exchangerate.cbr.ValCurs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.just_project.project_exchangerate.enums.ESource.CBR_RU_DAILY_ENG_XML;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class XmlMapperServiceTest {

    @Autowired
    private XmlMapperService xmlMapperService;

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
