package com.example.just_project.project_mail.controller;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.project_mail.service.EmailServiceApp;
import com.example.just_project.project_mail.dto.SendingMailDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Сервис сообщений", description = "Сервис сообщений")
@RequestMapping("/api/mail")
@RestController
@RequiredArgsConstructor
@Log4j2
public class EmailController {

    @NonNull
    private final EmailServiceApp emailServiceApp;

    @PostMapping("/send-message")
    @TrackExecutionTime
    public ResponseEntity<String> sendMessage(
            @RequestBody @Valid final SendingMailDto dto
    ) {
        emailServiceApp.sendMessage(dto);
        return ResponseEntity.ok("Message sent successfully");
    }
}
