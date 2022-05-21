package com.example.just_project.common.exceptionhandlers;

import com.example.just_project.util.ContractResult;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.HibernateException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
@Order(-10)
public class HibernateExceptionHandler {

    @ExceptionHandler(value = HibernateException.class)
    public ResponseEntity<ContractResult<Boolean>> handle(@NonNull HibernateException e) {
        log.error(e + e.getMessage() + ExceptionUtils.getStackTrace(e));

        val result = new ContractResult<Boolean>();
        if (ExceptionUtils.getRootCause(e) != null) {
            result.getViolations().add(ExceptionUtils.getRootCause(e).getMessage());
        } else {
            result.getViolations().add(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
