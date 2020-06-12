package com.esgi.stats19.api.soccer.teams.services;

import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.TeamRepository;
import com.esgi.stats19.api.soccer.teams.DTO.CreateTeamDTO;
import com.esgi.stats19.api.soccer.teams.DTO.UpdateTeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getTeams() {
        return this.teamRepository.findAll();
    }

    public Team getTeam(Integer teamId) {
        return this.teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("team " + teamId + " not exist"));
    }

    public List<Match> getMatches(Integer teamId) {
        return this.getTeam(teamId).getTeamMatches().stream().map(TeamMatch::getMatch).collect(Collectors.toList());
    }

    public Team createTeam(CreateTeamDTO teamDT0) {
        var team = Team.builder()
                .name(teamDT0.getName())
                .build();

        return this.teamRepository.save(team);
    }

    public Team updateTeam(UpdateTeamDTO teamDTO, Integer teamId) {
        var team = this.getTeam(teamId);
        team.setName(teamDTO.getName() != null ? teamDTO.getName() : team.getName());
        team.setShortName(teamDTO.getShortName() != null ? teamDTO.getShortName() : team.getShortName());
        team.setTeamApiId(teamDTO.getTeamApiId() != null ? teamDTO.getTeamApiId() : team.getTeamApiId());
        team.setTeamFifaId(teamDTO.getTeamFifaId() != null ? teamDTO.getTeamFifaId() : team.getTeamFifaId());

        return this.teamRepository.save(team);
    }

    public void deleteTeam(Integer teamId) {
        this.teamRepository.deleteById(teamId);
    }
}
