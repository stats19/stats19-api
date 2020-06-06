package com.esgi.stats19.api.soccer.matches.controllers;

import com.esgi.stats19.api.bets.DTO.GetBetDTO;
import com.esgi.stats19.api.bets.services.BetDTOService;
import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.soccer.matches.DTO.GetMatchDTO;
import com.esgi.stats19.api.soccer.matches.DTO.GetMatchPlayersDTO;
import com.esgi.stats19.api.soccer.matches.services.MatchDTOService;
import com.esgi.stats19.api.soccer.matches.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<GetMatchDTO> getMatches(@RequestParam Optional<Boolean> played) {
        List<Match> matches;
        if (played.isPresent()) {
            System.out.println(played.get());
            matches = this.matchService.getMatches(played.get());
        } else {
            matches = this.matchService.getMatches();
        }

        return this.matchDTOService.toResponse(matches);
    }

    @GetMapping("/{matchId}")
    public GetMatchDTO getMatch(@PathVariable Integer matchId) {
        return this.matchDTOService.toResponse(this.matchService.getMatch(matchId));
    }

    @GetMapping("/{matchId}/players")
    public GetMatchPlayersDTO getPlayers(@PathVariable Integer matchId) {
        var match = this.matchService.getMatch(matchId);
        return this.matchDTOService.toResponse(this.matchService.getHomePlayers(match),
                this.matchService.getAwayPlayers(match));
    }

    @GetMapping("/{matchId}/bets")
    public List<GetBetDTO> getBets(@PathVariable Integer matchId) {
        return this.betDTOService.betToResponse(this.matchService.getBets(matchId));
    }
}
