package com.example.just_project.project_exchangerate.controllers.api;

import com.example.just_project.project_exchangerate.services.CachingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Кэш контроллер", description = "Работа с кэшем через API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/exchangerate/db", produces = MediaType.APPLICATION_JSON_VALUE)
public class CachingController {

    @NonNull
    private final CachingService cachingService;

    @Operation(summary = "Очистить все кэши", description = "Очистить все кэши")
    @GetMapping("clearAllCaches")
    public void clearAllCaches() {
        cachingService.clearAllCaches();
    }
}
