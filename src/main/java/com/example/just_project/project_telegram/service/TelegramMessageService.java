package com.example.just_project.project_telegram.service;

import com.example.just_project.project_telegram.dto.TelegramSendMessageDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Validated
public class TelegramMessageService {

    @NonNull
    private final RestTemplate restTemplate;

    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.send-msg-post-url}")
    private String sendMsgPostUrl;

    public void sendMessage(@NonNull TelegramSendMessageDto dto) {
        val url = String.format(sendMsgPostUrl, botToken, dto.getAddress(), dto.getText());
        restTemplate.postForEntity(url, null, String.class);
    }
}
