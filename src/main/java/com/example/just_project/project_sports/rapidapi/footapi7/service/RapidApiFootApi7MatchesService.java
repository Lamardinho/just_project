package com.example.just_project.project_sports.rapidapi.footapi7.service;

import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.util.CacheNames;
import com.example.just_project.project_sports.rapidapi.footapi7.dto.EventsDTO;
import com.example.just_project.project_sports.rapidapi.footapi7.entity.MatchSchedule;
import com.example.just_project.project_sports.rapidapi.footapi7.repository.MatchScheduleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Сервис для работы с API: RapidApi/FootApi7
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class RapidApiFootApi7MatchesService {

    @NonNull
    private final RestTemplate restTemplate;
    @NonNull
    private final ObjectMapperService objectMapperService;
    @NonNull
    private final MatchScheduleRepository matchScheduleRepository;

    @Value("${app.rapid-api.key}")
    private String rapidApiKey;

    public static final String RAPID_API_KEY_HEADER_NAME = "X-Rapidapi-Key";
    public static final String RAPID_API_HOST_HEADER_NAME = "X-Rapidapi-Host";
    public static final String RAPID_API_FOOTAPI7_HOST_HEADER_VALUE = "footapi7.p.rapidapi.com";

    /**
     * Получить расписание на указанную дату.
     * Все параметры являются обязательными, т.к. по ним формируется кеш.
     *
     * @param category* - категория чемпионата
     * @param day*      - день
     * @param month*    - месяц
     * @param year*     - год
     */
    @Cacheable(value = CacheNames.RAPID_API_FOOT_API_7_MATCHES_SERVICE_GET_MATCH_SCHEDULE_AND_RESULTS)
    public EventsDTO getMatchScheduleAndResults(int category, int day, int month, int year) {
        val date = LocalDate.of(year, month, day);
        val dateSchedule = matchScheduleRepository.findByDate(date);

        if (dateSchedule.isPresent()) {
            return dateSchedule.get().getEvents();
        } else {
            val response = getMatchScheduleAndResultsResponseEntity(category, day, month, year);
            return objectMapperService.readValue(response.getBody(), EventsDTO.class);
        }
    }

    public EventsDTO requestMatchSchedule(int category) {
        val today = LocalDate.now();
        val todaySchedule = matchScheduleRepository.findByDate(today);

        if (todaySchedule.isPresent()) {
            return todaySchedule.get().getEvents();
        } else {
            val response = getMatchScheduleAndResultsResponseEntity(
                    category, today.getDayOfMonth(), today.getMonthValue(), today.getYear()
            );
            // TODO: что если нет матчей? каков результат если эмпти то отправить...?
            val body = response.getBody();
            val events = objectMapperService.readValue(body, EventsDTO.class);

            var matchSchedule = new MatchSchedule(null, today, events, 1);
            matchSchedule = matchScheduleRepository.save(matchSchedule);
            return matchSchedule.getEvents();
        }
    }

    /**
     * Получить расписание на указанную дату.
     * Все параметры являются обязательными, т.к. по ним формируется кеш.
     *
     * @param category* - категория чемпионата
     * @param day*      - день
     * @param month*    - месяц
     * @param year*     - год
     */
    //@Cacheable(value = CacheNames.RAPID_API_FOOT_API_7_MATCHES_SERVICE_GET_MATCH_SCHEDULE_AND_RESULTS_RESPONSE_ENTITY)
    @NonNull
    private ResponseEntity<String> getMatchScheduleAndResultsResponseEntity(int category, int day, int month, int year) {
        val headers = new HttpHeaders();
        headers.set(RAPID_API_KEY_HEADER_NAME, rapidApiKey);
        headers.set(RAPID_API_HOST_HEADER_NAME, RAPID_API_FOOTAPI7_HOST_HEADER_VALUE);
        val url =
                format("https://footapi7.p.rapidapi.com/api/category/%s/events/%s/%s/%s", category, day, month, year);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
    }

    public void getTodayUefaMatchSchedule() {
        val events = requestMatchSchedule(1465);
        val uefaEvents = events
                .getEvents()
                .stream()
                .filter(it -> it.getTournament() != null && it.getTournament().getName().equals("UEFA Champions League"))
                .collect(Collectors.toSet());

        log.info(uefaEvents.size());

        if (!events.getEvents().isEmpty()) {
            System.out.println("Сегодня есть матчи");   // TODO: отправить сообщение
        }
    }
}