package com.example.just_project.project_exchangerate.dto.exchangerate;

import com.example.just_project.project_exchangerate.enums.ERate;
import lombok.Data;

import java.io.Serializable;

@Data
public class RateDto implements Serializable {

    private String valuteId;

    private ERate charCode;

    private String name;

    private Double value;
}
