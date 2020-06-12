package com.esgi.stats19.api.soccer.players.controllers;

import com.esgi.stats19.api.soccer.matches.DTO.GetMatchDTO;
import com.esgi.stats19.api.soccer.matches.services.MatchDTOService;
import com.esgi.stats19.api.soccer.players.DTO.GetPlayerDTO;
import com.esgi.stats19.api.soccer.players.services.PlayerDTOService;
import com.esgi.stats19.api.soccer.players.services.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerAPIController {

    private final PlayerDTOService playerDTOService;
    private final PlayerService playerService;
    private final MatchDTOService matchDTOService;

    public PlayerAPIController(PlayerDTOService playerDTOService, PlayerService playerService,
                               MatchDTOService matchDTOService) {
        this.playerDTOService = playerDTOService;
        this.playerService = playerService;
        this.matchDTOService = matchDTOService;
    }

    @GetMapping
    public List<GetPlayerDTO> getPlayers() {
        return this.playerDTOService.toResponse(this.playerService.getPlayers());
    }

    @GetMapping("/{playerId}")
    public GetPlayerDTO getPlayerDTO(@PathVariable Integer playerId) {
        return this.playerDTOService.toResponse(this.playerService.getPlayer(playerId));
    }

    @GetMapping("/{playerId}/matches")
    public List<GetMatchDTO> getMatches(@PathVariable Integer playerId) {
        return this.matchDTOService.toResponse(this.playerService.getMatches(playerId));
    }


}
