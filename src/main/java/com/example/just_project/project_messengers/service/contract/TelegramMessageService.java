package com.example.just_project.project_messengers.service.contract;

import com.example.just_project.project_messengers.dto.TelegramSendMessageDto;
import lombok.NonNull;

public interface TelegramMessageService {
    void sendMessage(@NonNull TelegramSendMessageDto dto);
}
