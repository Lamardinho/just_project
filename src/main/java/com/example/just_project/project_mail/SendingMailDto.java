package com.example.just_project.project_mail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SendingMailDto {

    @Schema(description = "адрес почты кому отправляет")
    @Email
    @NotBlank
    private String address;

    private String subject = "(no subject)";

    private String text = "";
}
