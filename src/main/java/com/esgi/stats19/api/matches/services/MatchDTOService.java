package com.esgi.stats19.api.matches.services;

import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.exceptions.InternalErrorException;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.matches.DTO.GetMatchDTO;
import com.esgi.stats19.api.matches.DTO.GetTeamMatchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchDTOService {

    private final URIService uriService;

    @Autowired
    public MatchDTOService(URIService uriService){
        this.uriService = uriService;
    }

    public GetMatchDTO toResponse(@NotNull Match match) {
        var home = match.getTeamMatches()
                .stream().filter(TeamMatch::isHome).findFirst()
                .orElseThrow(() -> new InternalErrorException("Internal Error"));

        var away = match.getTeamMatches()
                .stream().filter(team -> !team.isHome()).findFirst()
                .orElseThrow(() -> new InternalErrorException("Internal Error"));

        return GetMatchDTO.builder()
                .matchId(match.getMatchId())
                .matchFifaId(match.getMatch_api_id())
                .CountryName(match.getCountry().getName())
                .leagueName(match.getLeague().getName())
                .season(match.getSeason())
                .stage(match.getStage())
                .date(match.getDate().toString())
                .status(match.isStatus())
                .home(this.toResponse(home))
                .away(this.toResponse(away))
                .bets(this.uriService.getBetsByMatch(match.getMatchId()).toString())
                .build();
    }

    public List<GetMatchDTO> toResponse(@NotNull List<Match> matches) {
        return matches.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public GetTeamMatchDTO toResponse(@NotNull TeamMatch teamMatch) {
        return GetTeamMatchDTO.builder()
                .teamId(teamMatch.getTeam().getTeamId())
                .name(teamMatch.getTeam().getName())
                .goals(teamMatch.getGoals())
                .possession(teamMatch.getPossession())
                .team(this.uriService.getTeam(teamMatch.getTeam().getTeamId()).toString())
                .players(this.uriService
                        .getPlayerByTeamMatch(teamMatch.getMatch().getMatchId(),
                                teamMatch.getTeam().getTeamId()).toString())
                .build();
    }

}
