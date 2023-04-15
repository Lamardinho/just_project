package com.example.just_project.project_sports.rapidapi.footapi7.controller;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.common.util.ContractResult;
import com.example.just_project.project_sports.rapidapi.footapi7.dto.EventsDTO;
import com.example.just_project.project_sports.rapidapi.footapi7.service.RapidApiFootApi7MatchesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "soccer-rapid-footapi7", description = "soccer-rapid-footapi7")
@RestController
@RequestMapping("/soccer/rapid-footapi7")
@RequiredArgsConstructor
public class RapidApiFootApi7MatchesController {

    @NonNull
    private final RapidApiFootApi7MatchesService service;

    /**
     * @param categoryId - категории чемпионатов. <a href="https://footapi7.p.rapidapi.com/api/tournament/categories">...</a>
     * @param day        -
     * @param month      -
     * @param year       -
     */
    @Operation(
            summary = "match_schedule_and_results",
            description = "Предоставляет расписание матчей и их результаты по указанной дате и категории чемпионата."
    )
    @GetMapping("/events/category/{id}/date/{day}/{month}/{year}")
    @TrackExecutionTime
    public ContractResult<EventsDTO> getMatchScheduleAndResults(
            @PathVariable("id") int categoryId,
            @PathVariable("day") int day,
            @PathVariable("month") int month,
            @PathVariable("year") int year
    ) {
        val result = service.getMatchScheduleAndResults(categoryId, day, month, year);
        return new ContractResult<>(result);
    }
}
