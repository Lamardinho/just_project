package com.example.just_project.project_exchangerate.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    @Deprecated(since = "0.0.1")    //не официальный источник
    public static final String RUBLE_CBR_DAILY_RU_URL = "https://www.cbr-xml-daily.ru/latest.js";
    public static final String CBR_XML_DAILY_ENG_BY_DATE_URL = "https://www.cbr.ru/scripts/XML_daily_eng.asp?date_req=";
}
