package com.esgi.stats19.api.soccer.players.controllers;

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

    public PlayerProcessController(PlayerService playerService, PlayerDTOService playerDTOService) {
        this.playerService = playerService;
        this.playerDTOService = playerDTOService;
    }

    @PostMapping("{playerId}/score")
    public GetPlayerDTO updateScore(@RequestBody UpdateScoreDTO scoreDTO, @PathVariable Integer playerId) {
        return playerDTOService.toResponse(playerService.updateScore(playerId, scoreDTO.getScore()));
    }
}
