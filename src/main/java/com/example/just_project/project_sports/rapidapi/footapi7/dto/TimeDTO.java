package com.example.just_project.project_sports.rapidapi.footapi7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeDTO {
    private long currentPeriodStartTimestamp;
    private int injuryTime1;
    private int injuryTime2;
}
