package com.esgi.stats19.api.soccer.leagues.DTO;

import com.esgi.stats19.api.soccer.teams.DTO.GetPlayedMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchByLeague {
    private Integer leagueId;
    private String leagueName;
    private List<GetLeagueMatch> matches;
    private List<GetPlayedMatch> playedMatches;
}
