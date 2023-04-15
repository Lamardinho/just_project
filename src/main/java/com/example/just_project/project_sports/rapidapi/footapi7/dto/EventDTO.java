package com.example.just_project.project_sports.rapidapi.footapi7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private int awayRedCards;
    private ScoreDTO awayScore;
    private TeamDTO awayTeam;
    private ChangesDTO changes;
    private String customId;
    private int detailId;
    private boolean finalResultOnly;
    private boolean hasEventPlayerHeatMap;
    private boolean hasEventPlayerStatistics;
    private boolean hasGlobalHighlights;
    private boolean hasXg;
    private ScoreDTO homeScore;
    private TeamDTO homeTeam;
    private int id;
    private RoundInfoDTO roundInfo;
    private String slug;
    private long startTimestamp;
    private StatusDTO status;
    private TimeDTO time;
    private TournamentDTO tournament;
    private int winnerCode;
}
