package com.example.just_project.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ContractResult<T> {

    @Schema(description = "Response body/Тело ответа")
    private T data;

    @Schema(description = "Error messages/Сообщения ошибок")
    private List<String> violations = new ArrayList<>();

    @Schema(description = "Additional message/Additional message")
    private String message;

    public ContractResult(T data) {
        this.setData(data);
    }
}
