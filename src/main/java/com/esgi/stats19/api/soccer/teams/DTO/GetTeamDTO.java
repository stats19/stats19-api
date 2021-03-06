package com.esgi.stats19.api.soccer.teams.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamDTO {
    private Integer teamId;
    private GetTeamLeague league;
    private String name;
    private String shortName;
    private Integer matchesPlayed;
    private Integer matchesWin;
    private Integer matchesDraw;
    private Integer matchesLose;
    private Integer homeWin;
    private Integer awayWin;
    private Integer goals;
    private Integer goalsConceded;
    private Integer foul;
    private String picture;
    private List<GetRecentMatch> recentMatches;
    private List<GetPlayedMatch> playedMatches;
    private List<GetNextMatch> nextMatches;
}
