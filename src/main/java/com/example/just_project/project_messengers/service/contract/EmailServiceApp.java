package com.example.just_project.project_messengers.service.contract;

import com.example.just_project.project_messengers.dto.SendingMailDto;
import lombok.NonNull;

public interface EmailServiceApp {
    void sendMessage(@NonNull SendingMailDto dto);
}
