package com.esgi.stats19.api.soccer.leagues.services;

import com.esgi.stats19.api.common.entities.League;
import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.leagues.DTO.GetLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.GetLeagueMatch;
import com.esgi.stats19.api.soccer.leagues.DTO.GetMatchByLeague;
import com.esgi.stats19.api.soccer.matches.services.MatchDTOService;
import com.esgi.stats19.api.soccer.matches.services.MatchService;
import com.esgi.stats19.api.soccer.teams.DTO.GetPlayedMatch;
import com.esgi.stats19.api.soccer.teams.services.TeamDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueDTOService {

    private final URIService uriService;
    private final MatchService matchService;
    private final TeamDTOService teamDTOService;

    @Autowired
    public LeagueDTOService(URIService uriService, MatchService matchService, TeamDTOService teamDTOService) {
        this.uriService = uriService;
        this.matchService = matchService;
        this.teamDTOService = teamDTOService;
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

    public GetMatchByLeague getMatchDto(@NotNull List<Match> matches, League league, boolean played) {
        return GetMatchByLeague.builder()
                .leagueId(league.getLeagueId())
                .leagueName(league.getName())
                .matches(!played ? matches.stream().map(this::getMatchDto).collect(Collectors.toList()) : new ArrayList<>())
                .playedMatches(played ? getPlayedMatches(matches) : new ArrayList<>())
                .build();
    }

    public List<GetPlayedMatch> getPlayedMatches(List<Match> playedMatches) {
        return playedMatches.stream().map(this::getPlayedMatch).collect(Collectors.toList());
    }

    public GetPlayedMatch getPlayedMatch(Match match) {
        return GetPlayedMatch.builder()
                .matchId(match.getMatchId())
                .date(match.getDate())
                .home(teamDTOService.getPlayedMatchTeam(match, true))
                .away(teamDTOService.getPlayedMatchTeam(match, false))
                .build();
    }
    public GetLeagueMatch getMatchDto(@NotNull Match match) {
        return GetLeagueMatch.builder()
                .step(match.getStage())
                .resultMatch(match.getForecast())
                .homeName(this.matchService.getHomeTeam(match).getTeam().getName())
                .awayName(this.matchService.getAwayTeam(match).getTeam().getName())
                .date(match.getDate())
                .build();
    }

}
