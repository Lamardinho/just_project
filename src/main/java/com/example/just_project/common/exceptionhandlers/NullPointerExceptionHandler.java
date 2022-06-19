package com.example.just_project.common.exceptionhandlers;

import com.example.just_project.util.ContractResult;
import com.example.just_project.util.AppMsgErrors;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
@Order(-10)
public class NullPointerExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ContractResult<String>> handle(@NonNull Exception ex) {
        log.error(AppMsgErrors.AN_UNEXPECTED_ERROR_OCCURRED_EN, ex);

        val contractResult = new ContractResult<String>();
        contractResult.getViolations().add(AppMsgErrors.AN_UNEXPECTED_ERROR_OCCURRED_RU);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(contractResult);
    }
}
