package com.esgi.stats19.api.soccer.players.services;

import com.esgi.stats19.api.common.entities.Player;
import com.esgi.stats19.api.common.entities.Shot;
import com.esgi.stats19.api.common.enums.Card;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.players.DTO.GetFantasyDTO;
import com.esgi.stats19.api.soccer.players.DTO.GetPlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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
                .age(calculateAge(player.getBirthday().toString()))
                .weight(player.getWeight())
                .height(player.getHeight())
                .score(player.getScoreAverage())
                .playedMatches(player.getTeamsMatchesPlayers().size())
                .fouls(player.getTeamsMatchesPlayers().stream().reduce(0,
                        (count, teamMatchPlayer) -> count + teamMatchPlayer.getCulprits().size(), Integer::sum))
                .yellowCards(player.getTeamsMatchesPlayers().stream().reduce(0,
                        (count, teamMatchPlayer) ->
                                Math.toIntExact(count + teamMatchPlayer.getCulprits().stream()
                                        .filter(foul -> foul.getCard() == Card.YELLOW_CARD).count()), Integer::sum))
                .redCards(player.getTeamsMatchesPlayers().stream().reduce(0,
                        (count, teamMatchPlayer) ->
                                Math.toIntExact(count + teamMatchPlayer.getCulprits().stream()
                                        .filter(foul -> foul.getCard() == Card.RED_CARD).count()), Integer::sum))
                .shotOnTarget(player.getTeamsMatchesPlayers().stream().reduce(0,
                        (count, teamMatchPlayer) ->
                                Math.toIntExact(count + teamMatchPlayer.getScored().stream()
                                        .filter(Shot::isOnTarget).count()), Integer::sum))
                .goals(player.getTeamsMatchesPlayers().stream().reduce(0,
                        (count, teamMatchPlayer) ->
                                Math.toIntExact(count + teamMatchPlayer.getScored().stream()
                                        .filter(Shot::isScored).count()), Integer::sum))
                .matches(this.uriService.getMatchesByPlayer(player.getPlayerId()).toString())
                .build();
    }

    public List<GetPlayerDTO> toResponse(List<Player> players) {
        return players.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private int calculateAge(String birthDate) {
        var birthDay = LocalDate.parse(birthDate);
        var current = LocalDate.of(2016, 1, 1);
        return Period.between(birthDay, current).getYears();
    }
}
