package com.example.just_project.project_messengers.controller;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.project_messengers.dto.TelegramSendMessageDto;
import com.example.just_project.project_messengers.service.contract.TelegramMessageService;
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

@Tag(name = "Сервис telegram сообщений", description = "Сервис telegram сообщений")
@RequestMapping("/api/telegram")
@RestController
@RequiredArgsConstructor
@Log4j2
public class TelegramController {

    @NonNull
    private final TelegramMessageService messageService;

    @PostMapping("/send-message")
    @TrackExecutionTime
    public ResponseEntity<String> sendMessage(
            @RequestBody @Valid final TelegramSendMessageDto dto
    ) {
        messageService.sendMessage(dto);
        return ResponseEntity.ok("Message sent successfully");
    }
}