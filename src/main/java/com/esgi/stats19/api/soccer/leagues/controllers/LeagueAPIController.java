package com.esgi.stats19.api.soccer.leagues.controllers;

import com.esgi.stats19.api.soccer.leagues.DTO.GetLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.GetMatchByLeague;
import com.esgi.stats19.api.soccer.leagues.DTO.GetRankingDTO;
import com.esgi.stats19.api.soccer.leagues.services.LeagueDTOService;
import com.esgi.stats19.api.soccer.leagues.services.LeagueService;
import com.esgi.stats19.api.soccer.matches.services.MatchDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
public class LeagueAPIController {

    private final LeagueService leagueService;
    private final LeagueDTOService leagueDTOService;
    private final MatchDTOService matchDTOService;

    @Autowired
    public LeagueAPIController(LeagueService leagueService, LeagueDTOService leagueDTOService, MatchDTOService matchDTOService) {
        this.leagueService = leagueService;
        this.leagueDTOService = leagueDTOService;
        this.matchDTOService = matchDTOService;
    }

    @GetMapping
    public List<GetLeagueDTO> getLeagues() {
        return this.leagueDTOService.toResponse(this.leagueService.getLeagues());
    }

    @GetMapping("/{leagueId}")
    public GetLeagueDTO getLeague(@PathVariable Integer leagueId) {
        return this.leagueDTOService.toResponse(this.leagueService.getLeague(leagueId));
    }

    @GetMapping("/{leagueId}/matches")
    public GetMatchByLeague getMatches(@PathVariable Integer leagueId, @RequestParam(value = "played", required = false) Boolean played) {
        var league = leagueService.getLeague(leagueId);
        if (played == null) played = false;
        var matches = leagueService.getMatches(league, played);
        return this.leagueDTOService.getMatchDto(matches, league, played);
    }
    @GetMapping("/{leagueId}/ranking")
    public GetRankingDTO getRanking(@PathVariable Integer leagueId, @RequestParam(value = "season", required = false) String season) {
        return leagueService.getRankingByLeague(leagueId, season);
    }
}
