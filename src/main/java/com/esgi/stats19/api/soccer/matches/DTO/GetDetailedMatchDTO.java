package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDetailedMatchDTO {
    private Integer matchId;
    private Integer matchFifaId;

    private String season;
    private Integer stage;
    private String date;

    private GetTeamMatchDTO home;
    private GetTeamMatchDTO away;

    private String leagueName;
    private String CountryName;

    private String bets;

    private boolean status;
}
