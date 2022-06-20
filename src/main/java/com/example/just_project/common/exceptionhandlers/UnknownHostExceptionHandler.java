package com.example.just_project.common.exceptionhandlers;

import com.example.just_project.util.AppMsgErrors;
import com.example.just_project.util.ContractResult;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.UnknownHostException;
import java.util.List;

import static java.lang.String.format;

@Log4j2
@ControllerAdvice
public class UnknownHostExceptionHandler {

    @ExceptionHandler(value = UnknownHostException.class)
    public ResponseEntity<ContractResult<String>> handle(@NonNull Exception ex) {
        val message = format(AppMsgErrors.FAILED_TO_CONNECT_TO_URL, ex.getMessage());
        log.error(message, ex);

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ContractResult<>(List.of(message)));
    }
}
