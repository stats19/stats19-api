package com.esgi.stats19.api.soccer.leagues.controllers;

import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.leagues.DTO.CreateLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.GetLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.UpdateLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.services.LeagueDTOService;
import com.esgi.stats19.api.soccer.leagues.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/leagues")
public class LeagueAdminController {

    private final LeagueService leagueService;
    private final LeagueDTOService leagueDTOService;
    private final URIService uriService;

    @Autowired
    public LeagueAdminController(LeagueService leagueService, LeagueDTOService leagueDTOService, URIService uriService) {
        this.leagueService = leagueService;
        this.leagueDTOService = leagueDTOService;
        this.uriService = uriService;
    }

    @PostMapping
    public ResponseEntity<GetLeagueDTO> createLeague(@Validated @RequestBody CreateLeagueDTO league) {
        var createdLeague = this.leagueDTOService.toResponse(this.leagueService.createLeague(league));
        return ResponseEntity.created(this.uriService.getLeague(createdLeague.getLeagueId()))
                .body(createdLeague);
    }

    @PutMapping("/{leagueId}")
    public GetLeagueDTO updateLeague(@Validated @RequestBody UpdateLeagueDTO updateLeague,
                                     @PathVariable Integer leagueId) {
        return this.leagueDTOService.toResponse(this.leagueService.updateLeague(updateLeague, leagueId));
    }

    @DeleteMapping("/{leagueId}")
    public void deleteLeague(@PathVariable Integer leagueId){
        this.leagueService.deleteLeague(leagueId);
    }
}
