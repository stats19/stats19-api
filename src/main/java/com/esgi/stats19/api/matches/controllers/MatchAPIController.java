package com.esgi.stats19.api.matches.controllers;

import com.esgi.stats19.api.bets.DTO.BetDTO;
import com.esgi.stats19.api.bets.DTO.GetBetDTO;
import com.esgi.stats19.api.bets.services.BetDTOService;
import com.esgi.stats19.api.matches.DTO.GetMatchDTO;
import com.esgi.stats19.api.matches.services.MatchDTOService;
import com.esgi.stats19.api.matches.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchAPIController {

    private final MatchService matchService;
    private final MatchDTOService matchDTOService;
    private final BetDTOService betDTOService;

    @Autowired
    public MatchAPIController(MatchService matchService, MatchDTOService matchDTOService, BetDTOService betDTOService) {
        this.matchService = matchService;
        this.matchDTOService = matchDTOService;
        this.betDTOService = betDTOService;
    }

    @GetMapping
    public List<GetMatchDTO> getMatches() {
        return this.matchDTOService.toResponse(this.matchService.getMatches());
    }

    @GetMapping("/{matchId}")
    public GetMatchDTO getMatch(@PathVariable Integer matchId) {
        return this.matchDTOService.toResponse(this.matchService.getMatch(matchId));
    }

    @GetMapping("/{matchId}/bets")
    public List<GetBetDTO> getBets(@PathVariable Integer matchId) {
        return this.betDTOService.betToResponse(this.matchService.getBets(matchId));
    }
}
