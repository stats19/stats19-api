package com.esgi.stats19.api.soccer.teams.services;

import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.matches.services.MatchService;
import com.esgi.stats19.api.soccer.teams.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamDTOService {

    private final URIService uriService;
    private final TeamService teamService;
    private final MatchService matchService;

    @Autowired
    public TeamDTOService(URIService uriService, TeamService teamService, MatchService matchService){
        this.uriService = uriService;
        this.teamService = teamService;
        this.matchService = matchService;
    }

    public GetTeamDTO toResponse(@NotNull Team team, String season) {
        var playedMatches = teamService.getSeasonMatches(team, season);
        var matches = teamService.countMatchResult(team, playedMatches);
        var nextMatches = teamService.getNextMatches(team);
        return GetTeamDTO.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .shortName(team.getShortName())
                .picture(team.getPicture())
                .league(teamService.getLeagueMatch(team))
                .goals(teamService.getGoals(team, playedMatches))
                .foul(teamService.getFouls(team, playedMatches))
                .matchesWin(matches.getWins().size())
                .matchesDraw(matches.getDraw().size())
                .matchesLose(matches.getLose().size())
                .matchesPlayed(playedMatches.size())
                .goalsConceded(teamService.getGoalsConceded(team, playedMatches))
                .awayWin(teamService.countMatches(matches.getWins(), false))
                .homeWin(teamService.countMatches(matches.getWins(), true))
                .playedMatches(playedMatches.stream().map(this::getPlayedMatch).collect(Collectors.toList()))
                .nextMatches(nextMatches.subList(0, Math.min(10, nextMatches.size()))
                        .stream().map(this::getNextMatch).collect(Collectors.toList()))
                .recentMatches(playedMatches.subList(0, Math.min(5, playedMatches.size()))
                        .stream().map(this::getRecentMatch).collect(Collectors.toList()))
                .build();
    }

    public GetPlayedMatch getPlayedMatch(TeamMatch teamMatch) {
        var match = teamMatch.getMatch();
        return GetPlayedMatch.builder()
                .matchId(match.getMatchId())
                .date(match.getDate())
                .resultMatch(teamService.getResultMatch(teamMatch))
                .forecast(match.getForecast())
                .home(getPlayedMatchTeam(match, true))
                .away(getPlayedMatchTeam(match, false))
                .build();
    }

    private GetRecentMatch getRecentMatch(TeamMatch teamMatch) {
        return new GetRecentMatch(teamMatch.getMatch().getMatchId(), teamService.getResultMatch(teamMatch));
    }

    private GetNextMatch getNextMatch(TeamMatch teamMatch) {
        var match = teamMatch.getMatch();
        Team home = matchService.getHomeTeam(match).getTeam();
        Team away = matchService.getAwayTeam(match).getTeam();
        return GetNextMatch.builder()
                .date(match.getDate())
                .stage(match.getStage())
                .homeTeam(home.getShortName())
                .homeTeamId(home.getTeamId())
                .homePicture(home.getPicture())
                .awayTeam(away.getShortName())
                .awayTeamId(away.getTeamId())
                .awayPicture(away.getPicture())
                .forecastMatch(teamService.getForecast(match.getForecast(), teamMatch.isHome()))
                .build();
    }

    public PlayedMatchTeam getPlayedMatchTeam(Match match, Boolean home) {
        TeamMatch teamMatch = null;
        if (home) {
            teamMatch =  matchService.getHomeTeam(match);
        } else {
            teamMatch = matchService.getAwayTeam(match);
        }
        var team = teamMatch.getTeam();
        return new PlayedMatchTeam(team.getTeamId(), team.getShortName(), teamMatch.getGoals(), team.getPicture());
    }

    public List<GetTeamDTO> toResponse(@NotNull List<Team> teams, String season) {
        return teams.stream().map(team -> toResponse(team, season)).collect(Collectors.toList());
    }

}
