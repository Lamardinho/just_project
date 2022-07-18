package com.example.just_project.project_exchangerate.services;

import com.example.just_project.common.aop.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Просто тестовый класс для изучения темы {@link Async}
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class AsyncTestStudyService {

    /**
     * Просто тестовый метод для изучения темы {@link Async}
     */
    @Async
    @TrackExecutionTime
    public synchronized void testAsyncMethod() {
        val list = List.of(3, 2, 1);
        list.forEach(
                it -> {
                    try {
                        wait(1000);
                        log.info(it);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        log.info("done!");
    }
}
