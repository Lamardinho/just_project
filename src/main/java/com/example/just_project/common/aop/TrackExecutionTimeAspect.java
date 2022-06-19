package com.example.just_project.common.aop;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static java.lang.System.currentTimeMillis;

@Aspect
@Component
@Log4j2
public class TrackExecutionTimeAspect {

    @Around("@annotation(com.example.just_project.common.aop.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        val startTime = currentTimeMillis();
        val object = point.proceed();
        log.info(point.getSignature() + ". " + "Track execution time: " + (currentTimeMillis() - startTime) + "ms");
        return object;
    }
}
