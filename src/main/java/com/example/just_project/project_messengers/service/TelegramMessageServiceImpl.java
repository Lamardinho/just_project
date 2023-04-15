package com.example.just_project.project_messengers.service;

import com.example.just_project.project_messengers.dto.TelegramSendMessageDto;
import com.example.just_project.project_messengers.service.contract.TelegramMessageService;
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
public class TelegramMessageServiceImpl implements TelegramMessageService {

    @NonNull
    private final RestTemplate restTemplate;

    @Value("${app.telegram.bot.token}")
    private String botToken;
    @Value("${app.telegram.bot.send-msg-post-url}")
    private String sendMsgPostUrl;

    @Override
    public void sendMessage(@NonNull TelegramSendMessageDto dto) {
        val url = String.format(sendMsgPostUrl, botToken, dto.getAddress(), dto.getText());
        restTemplate.postForEntity(url, null, String.class);
    }
}
