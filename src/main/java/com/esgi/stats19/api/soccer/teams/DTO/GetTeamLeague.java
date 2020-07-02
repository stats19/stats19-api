package com.esgi.stats19.api.soccer.teams.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTeamLeague {
    private Integer leagueId;
    private String leagueName;
    private String leaguePicture;
}
