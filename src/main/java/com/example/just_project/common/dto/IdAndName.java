package com.example.just_project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Used to optimize data for sending to the web
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class IdAndName implements Serializable {

    @NotNull
    private Long id;

    private String name = "";
}
