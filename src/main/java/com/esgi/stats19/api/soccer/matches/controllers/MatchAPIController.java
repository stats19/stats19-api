package com.esgi.stats19.api.soccer.matches.controllers;

import com.esgi.stats19.api.bets.DTO.GetBetDTO;
import com.esgi.stats19.api.bets.services.BetDTOService;
import com.esgi.stats19.api.common.broker.Sender;
import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.soccer.matches.DTO.GetMatchDTO;
import com.esgi.stats19.api.soccer.matches.DTO.GetMatchFormattedDTO;
import com.esgi.stats19.api.soccer.matches.DTO.GetMatchPlayersDTO;
import com.esgi.stats19.api.soccer.matches.services.MatchDTOService;
import com.esgi.stats19.api.soccer.matches.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchAPIController {

    private final MatchService matchService;
    private final MatchDTOService matchDTOService;
    private final BetDTOService betDTOService;
    private final Sender rabbitSender;

    @Autowired
    public MatchAPIController(MatchService matchService, MatchDTOService matchDTOService, BetDTOService betDTOService, Sender rabbitSender) {
        this.matchService = matchService;
        this.matchDTOService = matchDTOService;
        this.betDTOService = betDTOService;
        this.rabbitSender = rabbitSender;
    }

    @GetMapping
    public List<GetMatchDTO> getMatches(
            @RequestParam(required = false) Boolean played,
            @RequestParam(required = false) Boolean score
    ) {
        List<Match> matches;
        if (played != null) {
            System.out.println(played);
            matches = this.matchService.getMatches(played);
        } else {
            if (score != null && score) {
                matches = this.matchService.getMatchToScore();
            } else {
                matches = this.matchService.getMatches();
            }
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

    @GetMapping("/{matchId}/formatted")
    public GetMatchFormattedDTO getFormattedMatch(@PathVariable Integer matchId) {
        return this.matchService.getMatchFormattedDTO(matchId);
    }

    @GetMapping("/{matchId}/bets")
    public List<GetBetDTO> getBets(@PathVariable Integer matchId) {
        return this.betDTOService.betToResponse(this.matchService.getBets(matchId));
    }

    @PostMapping("/forecast")
    public void updateForecast() {
        this.rabbitSender.send("forecast", "production", "true");
    }
}
