package com.example.just_project.project_exchangerate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ESource {

    CBR_RU_DAILY_ENG_XML(
            "https://www.cbr.ru/scripts/XML_daily_eng.asp?date_req=",
            "Источник - официальный сайт банка России",
            "XML"
    ),
    CBR_NOT_OFFICIAL_XML_DAILY_RU_LATEST_JSON(
            "https://www.cbr-xml-daily.ru/latest.js",
            "Источник - некоммерческий проект, берут данные с cbr.ru",
            "JSON"
    );

    private final String url;
    private final String description;
    private final String dataType;
}
