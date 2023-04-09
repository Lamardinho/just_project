package com.example.just_project.project_telegram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;


@Schema(description = "Для отправки сообщений")
@Data
@AllArgsConstructor
public class TelegramSendMessageDto {

    @Schema(description = "адресат/чат id")
    @NonNull
    @NotBlank
    private String address;

    @Schema(description = "сообщение")
    @NonNull
    @NotBlank
    private String text;
}
