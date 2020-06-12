package com.esgi.stats19.api.soccer.players.services;

import com.esgi.stats19.api.common.entities.Player;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.players.DTO.GetPlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerDTOService {

    private final URIService uriService;

    @Autowired
    public PlayerDTOService(URIService uriService) {
        this.uriService = uriService;
    }
    public GetPlayerDTO toResponse(Player player) {
        return GetPlayerDTO.builder()
                .name(player.getName())
                .playerId(player.getPlayerId())
                .birthday(player.getBirthday())
                .weight(player.getWeight())
                .height(player.getHeight())
                .matches(this.uriService.getMatchesByPlayer(player.getPlayerId()).toString())
                .build();
    }

    public List<GetPlayerDTO> toResponse(List<Player> players) {
        return players.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
