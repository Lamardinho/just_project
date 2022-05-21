package com.example.just_project.common.exceptionhandlers;

import com.example.just_project.util.ContractResult;
import lombok.NonNull;
import lombok.val;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(-20)
public class ConstraintViolationExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ContractResult<Boolean>> handle(@NonNull MethodArgumentNotValidException e) {
        val result = new ContractResult<Boolean>();
        for (val error : e.getBindingResult().getFieldErrors()) {
            result.getViolations().add("field " + error.getField() + " " + error.getDefaultMessage());
        }
        for (val globalError : e.getBindingResult().getGlobalErrors()) {
            result.getViolations().add(globalError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
