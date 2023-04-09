package com.example.just_project.project_messengers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Schema(description = "для отправки сообщений")
@Data
public class SendingMailDto {

    @Schema(description = "адрес почты кому отправляем")
    @Email
    @NotBlank
    private String address;

    @Schema(description = "тема сообщения")
    private String subject = "(no subject)";

    @Schema(description = "текст сообщения")
    private String text = "";
}
