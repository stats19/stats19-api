package com.esgi.stats19.api.soccer.players.services;

import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Player;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return this.playerRepository.findAll();
    }

    public Player getPlayer(Integer playerId) {
        return this.playerRepository.findById(playerId)
                .orElseThrow(() -> new NotFoundException("not found player"));
    }

    public List<Match> getMatches(Integer playerId) {
        return this.getPlayer(playerId).getTeamsMatchesPlayers()
                .stream().map(teamMatchPlayer -> teamMatchPlayer.getTeamMatch().getMatch())
                .collect(Collectors.toList());
    }

    public Player updateScore(Integer playerId, Double score) {
        var player = getPlayer(playerId);
        player.setScoreAverage(score);
        return playerRepository.save(player);
    }

}
