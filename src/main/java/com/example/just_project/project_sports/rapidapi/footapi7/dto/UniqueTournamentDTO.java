package com.example.just_project.project_sports.rapidapi.footapi7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniqueTournamentDTO {
    private CategoryDTO category;
    private CountryDTO country;
    private boolean crowdsourcingEnabled;
    private boolean displayInverseHomeAwayTeams;
    private boolean hasEventPlayerStatistics;
    private boolean hasPerformanceGraphFeature;
    private int id;
    private String name;
    private String slug;
    private int userCount;
}
