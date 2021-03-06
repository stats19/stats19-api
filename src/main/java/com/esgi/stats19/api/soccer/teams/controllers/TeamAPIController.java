package com.esgi.stats19.api.soccer.teams.controllers;

import com.esgi.stats19.api.soccer.matches.DTO.GetMatchDTO;
import com.esgi.stats19.api.soccer.matches.services.MatchDTOService;
import com.esgi.stats19.api.soccer.teams.DTO.GetTeamDTO;
import com.esgi.stats19.api.soccer.teams.services.TeamDTOService;
import com.esgi.stats19.api.soccer.teams.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamAPIController {

    private final TeamService teamService;
    private final TeamDTOService teamDTOService;
    private final MatchDTOService matchDTOService;

    @Autowired
    public TeamAPIController(TeamService teamService, TeamDTOService teamDTOService,
                             MatchDTOService matchDTOService) {
        this.teamService = teamService;
        this.teamDTOService = teamDTOService;
        this.matchDTOService = matchDTOService;
    }

    @GetMapping
    public List<GetTeamDTO> getTeams(@RequestParam(value = "season", required = false) String season) {
        return this.teamDTOService.toResponse(this.teamService.getTeams(), season);
    }

    @GetMapping("/{teamId}")
    public GetTeamDTO getTeam(@PathVariable Integer teamId, @RequestParam(value = "season", required = false) String season) {
        return this.teamDTOService.toResponse(this.teamService.getTeam(teamId), season);
    }

    @GetMapping("/{teamId}/matches")
    public List<GetMatchDTO> getMatches(@PathVariable Integer teamId) {
        return this.matchDTOService.toResponse(this.teamService.getMatches(teamId));
    }
}
