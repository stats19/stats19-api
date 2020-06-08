package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchDTO {
    private Integer matchId;
    private int matchFifaId;

    private String season;
    private int stage;
    private String date;

    private GetTeamMatchDTO home;
    private GetTeamMatchDTO away;

    private String leagueName;
    private String CountryName;

    private String bets;

    private boolean played;
}
