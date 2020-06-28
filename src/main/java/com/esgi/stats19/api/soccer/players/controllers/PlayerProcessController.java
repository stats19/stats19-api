package com.esgi.stats19.api.soccer.players.controllers;

import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.soccer.players.DTO.GetPlayerDTO;
import com.esgi.stats19.api.soccer.players.DTO.UpdateScoreDTO;
import com.esgi.stats19.api.soccer.players.services.PlayerDTOService;
import com.esgi.stats19.api.soccer.players.services.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process/players")
public class PlayerProcessController {
    private final PlayerService playerService;
    private final PlayerDTOService playerDTOService;
    private final DateService dateService;

    public PlayerProcessController(PlayerService playerService, PlayerDTOService playerDTOService, DateService dateService) {
        this.playerService = playerService;
        this.playerDTOService = playerDTOService;
        this.dateService = dateService;
    }

    @PostMapping("{playerId}/score")
    public GetPlayerDTO updateScore(@RequestBody UpdateScoreDTO scoreDTO, @PathVariable Integer playerId,
                                    @RequestParam(required = false) String season) {
        season = season != null ? season : dateService.getSeason();
        return playerDTOService.toResponse(playerService.updateScore(playerId, scoreDTO.getScore()), season);
    }
}
