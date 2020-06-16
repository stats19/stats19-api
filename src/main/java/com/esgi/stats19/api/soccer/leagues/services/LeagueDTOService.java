package com.esgi.stats19.api.soccer.leagues.services;

import com.esgi.stats19.api.common.entities.League;
import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.exceptions.ServerErrorException;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.leagues.DTO.GetLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.GetLeagueMatch;
import com.esgi.stats19.api.soccer.leagues.DTO.GetMatchByLeague;
import com.esgi.stats19.api.soccer.matches.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueDTOService {

    private final URIService uriService;
    private final MatchService matchService;

    @Autowired
    public LeagueDTOService(URIService uriService, MatchService matchService) {
        this.uriService = uriService;
        this.matchService = matchService;
    }

    public GetLeagueDTO toResponse(@NotNull League league) {
        return GetLeagueDTO.builder()
                .leagueId(league.getLeagueId())
                .name(league.getName())
                .country(league.getCountry().getName())
                .matches(this.uriService.getLeague(league.getLeagueId()).toString())
                .build();
    }

    public List<GetLeagueDTO> toResponse(@NotNull List<League> leagues) {
        return leagues.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public GetMatchByLeague getMatchDto(@NotNull List<Match> matches) {
        var league = matches.stream().findFirst().orElseThrow(() -> new ServerErrorException("error")).getLeague();
        return GetMatchByLeague.builder()
                .leagueId(league.getLeagueId())
                .leagueName(league.getName())
                .matches(matches.stream().map(this::getMatchDto).collect(Collectors.toList()))
                .build();
    }

    public GetLeagueMatch getMatchDto(@NotNull Match match) {
        return GetLeagueMatch.builder()
                .matchId(match.getMatchId())
                .homeName(this.matchService.getHomeTeam(match).getTeam().getName())
                .awayName(this.matchService.getAwayTeam(match).getTeam().getName())
                .date(match.getDate())
                .build();
    }

}
