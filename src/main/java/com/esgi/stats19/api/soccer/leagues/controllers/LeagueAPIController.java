package com.esgi.stats19.api.soccer.leagues.controllers;

import com.esgi.stats19.api.soccer.leagues.DTO.GetLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.services.LeagueDTOService;
import com.esgi.stats19.api.soccer.leagues.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
public class LeagueAPIController {

    private final LeagueService leagueService;
    private final LeagueDTOService leagueDTOService;

    @Autowired
    public LeagueAPIController(LeagueService leagueService, LeagueDTOService leagueDTOService) {
        this.leagueService = leagueService;
        this.leagueDTOService = leagueDTOService;
    }

    @GetMapping
    public List<GetLeagueDTO> getLeagues() {
        return this.leagueDTOService.toResponse(this.leagueService.getLeagues());
    }

    @GetMapping("/{leagueId}")
    public GetLeagueDTO getLeague(@PathVariable Integer leagueId) {
        return this.leagueDTOService.toResponse(this.leagueService.getLeague(leagueId));
    }
}
