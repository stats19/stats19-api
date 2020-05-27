package com.esgi.stats19.api.teams.controllers;

import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.teams.DTO.CreateTeamDTO;
import com.esgi.stats19.api.teams.DTO.GetTeamDTO;
import com.esgi.stats19.api.teams.DTO.UpdateTeamDTO;
import com.esgi.stats19.api.teams.services.TeamDTOService;
import com.esgi.stats19.api.teams.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/teams")
public class TeamAdminController {

    private final TeamService teamService;
    private final TeamDTOService teamDTOService;
    private final URIService uriService;

    @Autowired
    public TeamAdminController(TeamService teamService, TeamDTOService teamDTOService, URIService uriService) {
        this.teamService = teamService;
        this.teamDTOService = teamDTOService;
        this.uriService = uriService;
    }

    @PostMapping
    public ResponseEntity<GetTeamDTO> createTeam(@Validated @RequestBody CreateTeamDTO team) {
        var createdTeam = this.teamDTOService.toResponse(this.teamService.createTeam(team));
        return ResponseEntity.created(this.uriService.getTeam(createdTeam.getTeamId()))
                .body(createdTeam);
    }

    @PutMapping("/{teamId}")
    public GetTeamDTO updateTeam(@Validated @RequestBody UpdateTeamDTO updateTeam,
                                     @PathVariable Integer teamId) {
        return this.teamDTOService.toResponse(this.teamService.updateTeam(updateTeam, teamId));
    }

    @DeleteMapping("/{teamId}")
    public void deleteTeam(@PathVariable Integer teamId){
        this.teamService.deleteTeam(teamId);
    }
}
