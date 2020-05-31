package com.esgi.stats19.api.soccer.players.services;

import com.esgi.stats19.api.common.entities.Player;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> players() {
        return this.playerRepository.findAll();
    }

    public Player getPlayer(Integer playerId) {
        return this.playerRepository.findById(playerId)
                .orElseThrow(() -> new NotFoundException("not found player"));
    }


}
