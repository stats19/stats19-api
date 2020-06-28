package com.esgi.stats19.api.soccer.players.services;

import com.esgi.stats19.api.common.entities.Player;
import com.esgi.stats19.api.common.entities.Shot;
import com.esgi.stats19.api.common.enums.Card;
import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.players.DTO.GetPlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerDTOService {

    private final URIService uriService;
    private final DateService dateService;
    private final PlayerService playerService;

    @Autowired
    public PlayerDTOService(URIService uriService, DateService dateService, PlayerService playerService) {
        this.uriService = uriService;
        this.dateService = dateService;
        this.playerService = playerService;
    }
    public GetPlayerDTO toResponse(Player player, String season) {
        var tmp = playerService.getActions(player, season);
        return GetPlayerDTO.builder()
                .name(player.getName())
                .playerId(player.getPlayerId())
                .age(calculateAge(player.getBirthday().toString()))
                .weight(player.getWeight())
                .height(player.getHeight())
                .score(player.getScoreAverage())
                .playedMatches(tmp.size())
                .fouls(tmp.stream().reduce(0,
                        (count, teamMatchPlayer) -> count + teamMatchPlayer.getCulprits().size(), Integer::sum))
                .yellowCards(tmp.stream().reduce(0,
                        (count, teamMatchPlayer) ->
                                Math.toIntExact(count + teamMatchPlayer.getCulprits().stream()
                                        .filter(foul -> foul.getCard() == Card.YELLOW_CARD).count()), Integer::sum))
                .redCards(tmp.stream().reduce(0,
                        (count, teamMatchPlayer) ->
                                Math.toIntExact(count + teamMatchPlayer.getCulprits().stream()
                                        .filter(foul -> foul.getCard() == Card.RED_CARD).count()), Integer::sum))
                .shotOnTarget(tmp.stream().reduce(0,
                        (count, teamMatchPlayer) ->
                                Math.toIntExact(count + teamMatchPlayer.getScored().stream()
                                        .filter(Shot::isOnTarget).count()), Integer::sum))
                .goals(tmp.stream().reduce(0,
                        (count, teamMatchPlayer) ->
                                Math.toIntExact(count + teamMatchPlayer.getScored().stream()
                                        .filter(Shot::isScored).count()), Integer::sum))
                .matches(this.uriService.getMatchesByPlayer(player.getPlayerId()).toString())
                .build();
    }

    public List<GetPlayerDTO> toResponse(List<Player> players, String season) {
        return players.stream().map(player -> toResponse(player, season)).collect(Collectors.toList());
    }

    private int calculateAge(String birthDate) {
        var birthDay = LocalDate.parse(birthDate);
        var current = LocalDate.ofInstant(dateService.today().toInstant(), ZoneId.systemDefault());
        return Period.between(birthDay, current).getYears();
    }
}
