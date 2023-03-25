package com.example.just_project.project_exchangerate.controllers.api;

import com.example.just_project.project_exchangerate.services.caching.CachingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "Кэш контроллер", description = "Работа с кэшем через API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/caches", produces = MediaType.APPLICATION_JSON_VALUE)
public class CachingController {

    @NonNull
    private final CachingService cachingService;

    @Operation(summary = "Посмотреть список кэшей", description = "Посмотреть список кэшей")
    @GetMapping("cache-names")
    public Collection<String> getCacheNames() {
        return cachingService.getCacheNames();
    }

    @Operation(summary = "Очистить все кэши", description = "Очистить все кэши")
    @DeleteMapping
    public void clearAllCaches() {
        cachingService.clearAllCaches();
    }
}
