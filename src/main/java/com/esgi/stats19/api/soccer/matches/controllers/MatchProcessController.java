package com.esgi.stats19.api.soccer.matches.controllers;

import com.esgi.stats19.api.soccer.matches.DTO.UpdateForecastDTO;
import com.esgi.stats19.api.soccer.matches.DTO.UpdateScorePlayer;
import com.esgi.stats19.api.soccer.matches.services.MatchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process/matches")
public class MatchProcessController {
    private final MatchService matchService;

    public MatchProcessController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PutMapping("/{matchId}/players/{playerId}")
    public void updatePlayerScore(@PathVariable Integer playerId, @RequestBody UpdateScorePlayer score, @PathVariable Integer matchId) {
        this.matchService.updatePlayerScore(matchId, playerId, score.getScore());
    }

    @PutMapping("/{matchId}")
    public void updateForecastMatch(@PathVariable Integer matchId, @RequestBody UpdateForecastDTO winner) {
        this.matchService.forecastMatch(matchId, winner.getForecast());
    }
}
