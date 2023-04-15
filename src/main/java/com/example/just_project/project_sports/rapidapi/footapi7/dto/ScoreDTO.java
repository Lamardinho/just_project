package com.example.just_project.project_sports.rapidapi.footapi7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDTO {
    private int current;
    private int display;
    private int normaltime;
    private int period1;
    private int period2;
}
