package com.esgi.stats19.api.soccer.teams.DTO;

import com.esgi.stats19.api.common.enums.ResultMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetNextMatch {
    private Date date;
    private Integer stage;
    private String homeTeam;
    private String awayTeam;
    private Integer homeTeamId;
    private Integer awayTeamId;
    private ResultMatch forecastMatch;
}
