package com.example.just_project.common.exceptionhandlers;

import com.example.just_project.common.util.ContractResult;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Log4j2
public class ForbiddenExceptionHandler {

    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<ContractResult<String>> handleAccessDeniedException(
            @NonNull HttpClientErrorException ex
    ) {
        log.error(ex.getMessage());
        val httpStatus = ex.getStatusCode();
        val contractResult = new ContractResult<String>();
        contractResult.getViolations().add(ex.getMessage());

        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(contractResult);
    }
}
