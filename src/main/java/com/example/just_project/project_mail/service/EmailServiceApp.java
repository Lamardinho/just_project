package com.example.just_project.project_mail.service;

import com.example.just_project.project_mail.dto.SendingMailDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceApp {

    @NonNull
    private final JavaMailSender emailSender;

    @Value("${my-app.mail-server.username}")
    private String appMailUserName;

    public void sendMessage(@NonNull final SendingMailDto dto) {
        val message = new SimpleMailMessage();
        message.setFrom(appMailUserName);
        message.setTo(dto.getAddress());
        message.setSubject(dto.getSubject());
        message.setText(dto.getText());

        emailSender.send(message);
    }
}
