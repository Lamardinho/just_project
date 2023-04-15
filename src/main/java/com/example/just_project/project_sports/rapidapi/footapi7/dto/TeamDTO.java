package com.example.just_project.project_sports.rapidapi.footapi7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private CountryDTO country;
    private boolean disabled;
    private String gender;
    private int id;
    private String name;
    private String nameCode;
    private boolean national;
    private String shortName;
    private String slug;
    private SportDTO sport;
    private List<Object> subTeams;
    private TeamColorsDTO teamColors;
    private int type;
    private int userCount;
}