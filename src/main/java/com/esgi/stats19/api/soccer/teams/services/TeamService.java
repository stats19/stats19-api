package com.esgi.stats19.api.soccer.teams.services;

import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.exceptions.ServerErrorException;
import com.esgi.stats19.api.common.repositories.TeamRepository;
import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.soccer.teams.DTO.CreateTeamDTO;
import com.esgi.stats19.api.soccer.teams.DTO.GetMatches;
import com.esgi.stats19.api.soccer.teams.DTO.UpdateTeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final DateService dateService;

    @Autowired
    public TeamService(TeamRepository teamRepository, DateService dateService) {
        this.teamRepository = teamRepository;
        this.dateService = dateService;
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

    public GetMatches countMatchResult(Team team) {
        var teamId = team.getTeamId();
        GetMatches matches = new GetMatches();

        getSeasonMatches(team).forEach(teamMatch -> {
            var goals = teamMatch.getGoals();
            var opponent = getOpponent(teamMatch.getMatch(), teamId).getGoals();

            if (goals > opponent) {
                matches.getWins().add(teamMatch);
            } else if (goals < opponent) {
                matches.getLose().add(teamMatch);
            } else {
                matches.getDraw().add(teamMatch);
            }
        });

        return matches;
    }

    public Integer countMatches(List<TeamMatch> matches, Boolean home) {
        return Math.toIntExact(matches.stream().filter(match -> match.isHome() == home).count());
    }

    public int countDiffScore(Team team) {
        return getSeasonMatches(team).stream().reduce(0, (counter, teamMatch2) -> {
            var goals = teamMatch2.getGoals();
            var opponent = getOpponent(teamMatch2.getMatch(), team.getTeamId()).getGoals();
            return counter + (goals - opponent);
        }, Integer::sum);
    }

    public List<TeamMatch> getSeasonMatches(Team team) {
        return team.getTeamMatches().stream()
                .filter(teamMatch -> matchIsSeason(teamMatch.getMatch()) && teamMatch.getMatch().isPlayed())
                .collect(Collectors.toList());
    }

    public Boolean matchIsSeason(Match match) {
        return match.getSeason().equals(dateService.getSeason());
    }

    public Integer getGoals(Team team) {
        return getSeasonMatches(team).stream()
                .reduce(0, (counter, match) -> counter + match.getGoals(), Integer::sum);
    }

    public Integer getGoalsConceded(Team team) {
        return getSeasonMatches(team).stream()
                .reduce(0, (counter, match) ->
                                counter + getOpponent(match.getMatch(), team.getTeamId()).getGoals()
                        , Integer::sum);
    }

    public Integer getFouls(Team team) {
        return getSeasonMatches(team).stream()
                .reduce(0, (fouls, teamMatch) -> fouls + teamMatch.getTeamsMatchesPlayers()
                                .stream().reduce(0, (playerFoul, player) ->
                                        playerFoul + player.getCulprits().size(), Integer::sum),
                        Integer::sum);
    }

    public TeamMatch getOpponent(Match match, Integer teamId) {
        return match.getTeamMatches().stream().filter(teamMatch -> !teamMatch.getTeam().getTeamId().equals(teamId))
                .findFirst().orElseThrow(() -> new ServerErrorException("no opponent founded"));
    }

    public void deleteTeam(Integer teamId) {
        this.teamRepository.deleteById(teamId);
    }
}
