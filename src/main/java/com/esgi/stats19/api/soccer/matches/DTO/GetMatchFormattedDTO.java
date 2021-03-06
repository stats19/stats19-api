package com.esgi.stats19.api.soccer.matches.DTO;

import com.esgi.stats19.api.common.enums.Winner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchFormattedDTO {
    private Integer matchId;
    private GetLeagueDTO league;
    private Winner forecast;
    private GetTeamMatchFormatted homeTeam;
    private GetTeamMatchFormatted awayTeam;
    private List<GetMatchDetailsFormattedDTO> details;
}
