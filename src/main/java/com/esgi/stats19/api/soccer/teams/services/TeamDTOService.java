package com.esgi.stats19.api.soccer.teams.services;

import com.esgi.stats19.api.common.entities.Team;
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

    @Autowired
    public TeamDTOService(URIService uriService){
        this.uriService = uriService;
    }

    public GetTeamDTO toResponse(@NotNull Team team) {
        return GetTeamDTO.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .shortName(team.getShortName())
                .teamFifaId(team.getTeamFifaId())
                .teamApiId(team.getTeamApiId())
                .matches(this.uriService.getMatchesByTeam(team.getTeamId()).toString())
                .build();
    }

    public List<GetTeamDTO> toResponse(@NotNull List<Team> teams) {
        return teams.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
