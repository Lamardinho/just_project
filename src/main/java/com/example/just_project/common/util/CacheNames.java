package com.example.just_project.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheNames {

    // RATES:
    public static final String GET_ACTUAL_RUBLE_RATES_BY_USD_AND_EURO = "getActualRubleRatesByUsdAndEuro";
    public static final String GET_RUBLE_RATES_FROM_CBR_XML_URL_BY_DATE = "getRubleRatesFromCbrXmlUrlByDate";
    public static final String GET_RUBLE_RATES_FROM_CBR_XML_URL_BY_DATE_FEIGN_CLIENT =
            "getRubleRatesFromCbrXmlUrlByDateFeignClient";

    // SPORTS:
    public static final String RAPID_API_FOOT_API_7_MATCHES_SERVICE_GET_MATCH_SCHEDULE_AND_RESULTS =
            "rapidApiFootApi7MatchesService_getMatchScheduleAndResults";
    public static final String RAPID_API_FOOT_API_7_MATCHES_SERVICE_GET_RESPONSE_ENTITY =
            "rapidApiFootApi7MatchesService_getResponseEntity";
}
