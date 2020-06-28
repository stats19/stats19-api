package com.esgi.stats19.api.soccer.players.controllers;

import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.soccer.matches.DTO.GetMatchDTO;
import com.esgi.stats19.api.soccer.matches.services.MatchDTOService;
import com.esgi.stats19.api.soccer.players.DTO.GetFantasyDTO;
import com.esgi.stats19.api.soccer.players.DTO.GetPlayerDTO;
import com.esgi.stats19.api.soccer.players.services.PlayerDTOService;
import com.esgi.stats19.api.soccer.players.services.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerAPIController {

    private final PlayerDTOService playerDTOService;
    private final PlayerService playerService;
    private final MatchDTOService matchDTOService;
    private final DateService dateService;
    public PlayerAPIController(PlayerDTOService playerDTOService, PlayerService playerService,
                               MatchDTOService matchDTOService, DateService dateService) {
        this.playerDTOService = playerDTOService;
        this.playerService = playerService;
        this.matchDTOService = matchDTOService;
        this.dateService = dateService;
    }

    @GetMapping
    public List<GetPlayerDTO> getPlayers(@RequestParam(required = false) String season) {
        season = season != null ? season : dateService.getSeason();
        return this.playerDTOService.toResponse(this.playerService.getPlayers(), season);
    }

    @GetMapping("/{playerId}")
    public GetPlayerDTO getPlayerDTO(@PathVariable Integer playerId, @RequestParam(required = false) String season) {
        season = season != null ? season : dateService.getSeason();
        return this.playerDTOService.toResponse(this.playerService.getPlayer(playerId), season);
    }

    @GetMapping("/{playerId}/matches")
    public List<GetMatchDTO> getMatches(@PathVariable Integer playerId) {
        return this.matchDTOService.toResponse(this.playerService.getMatches(playerId));
    }

    @GetMapping("/fantasy")
    public GetFantasyDTO getFantasy() {
        return playerService.getFantasyLeague();
    }


}
