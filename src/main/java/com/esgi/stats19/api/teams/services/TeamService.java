package com.esgi.stats19.api.teams.services;

import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.TeamRepository;
import com.esgi.stats19.api.countries.services.CountryService;
import com.esgi.stats19.api.teams.DTO.CreateTeamDTO;
import com.esgi.stats19.api.teams.DTO.UpdateTeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
