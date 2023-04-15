package com.example.just_project.project_messengers.service;

import com.example.just_project.project_messengers.dto.SendingMailDto;
import com.example.just_project.project_messengers.service.contract.EmailServiceApp;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceAppImpl implements EmailServiceApp {

    @NonNull
    private final JavaMailSender emailSender;

    @Value("${app.mail-server.username}")
    private String appMailUserName;

    @Override
    public void sendMessage(@NonNull final SendingMailDto dto) {
        val message = new SimpleMailMessage();
        message.setFrom(appMailUserName);
        message.setTo(dto.getAddress());
        message.setSubject(dto.getSubject());
        message.setText(dto.getText());

        emailSender.send(message);
    }
}
