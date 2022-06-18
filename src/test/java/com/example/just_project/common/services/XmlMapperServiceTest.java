package com.example.just_project.common.services;

import com.example.just_project.exchangerate.dto.exchangerate.cbr.ValCurs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.just_project.exchangerate.util.AppConstants.CBR_XML_DAILY_ENG_BY_DATE_URL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class XmlMapperServiceTest {

    @Autowired
    private XmlMapperService xmlMapperService;

    @Test
    void readXml_urlUrl() throws MalformedURLException {
        final var valCurs = xmlMapperService.readXml(new URL(CBR_XML_DAILY_ENG_BY_DATE_URL), ValCurs.class);

        assertNotNull(valCurs);
    }

    @Test
    void readXml_StringUrl() {
        final var valCurs = xmlMapperService.readXml(CBR_XML_DAILY_ENG_BY_DATE_URL, ValCurs.class);

        assertNotNull(valCurs);
    }
}
