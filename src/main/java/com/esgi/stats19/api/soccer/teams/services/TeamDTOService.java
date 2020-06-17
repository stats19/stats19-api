package com.esgi.stats19.api.soccer.teams.services;

import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.enums.ResultMatch;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.teams.DTO.GetTeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamDTOService {

    private final URIService uriService;
    private final TeamService teamService;

    @Autowired
    public TeamDTOService(URIService uriService, TeamService teamService){
        this.uriService = uriService;
        this.teamService = teamService;
    }

    public GetTeamDTO toResponse(@NotNull Team team) {
        var matches = teamService.countMatchResult(team);
        return GetTeamDTO.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .shortName(team.getShortName())
                .matches(this.uriService.getMatchesByTeam(team.getTeamId()).toString())
                .goals(teamService.getGoals(team))
                .foul(teamService.getFouls(team))
                .matchesWin(matches.getWins().size())
                .matchesDraw(matches.getDraw().size())
                .matchesLose(matches.getLose().size())
                .matchesPlayed(teamService.getSeasonMatches(team).size())
                .goalsConceded(teamService.getGoalsConceded(team))
                .awayWin(teamService.countMatches(matches.getWins(), false))
                .homeWin(teamService.countMatches(matches.getWins(), true))
                .build();
    }

    public List<GetTeamDTO> toResponse(@NotNull List<Team> teams) {
        return teams.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
