package com.esgi.stats19.api.soccer.players.services;

import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Player;
import com.esgi.stats19.api.common.entities.TeamMatchPlayer;
import com.esgi.stats19.api.common.enums.PlayerPosition;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.PlayerRepository;
import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.soccer.players.DTO.FantasyPlayerDTO;
import com.esgi.stats19.api.soccer.players.DTO.GetFantasyDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final DateService dateService;

    public PlayerService(PlayerRepository playerRepository, DateService dateService) {
        this.playerRepository = playerRepository;
        this.dateService = dateService;
    }

    public List<Player> getPlayers() {
        return this.playerRepository.findAll();
    }

    public Player getPlayer(Integer playerId) {
        return this.playerRepository.findById(playerId)
                .orElseThrow(() -> new NotFoundException("not found player"));
    }

    public List<TeamMatchPlayer> getActions(Player player, String season) {
        return playerRepository.getSeasonActions(player, season);
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

    public GetFantasyDTO getFantasyLeague() {
        var pageable = PageRequest.of(0, 5, Sort.by("scoreAverage").descending());
        return GetFantasyDTO.builder()
                .date(dateService.format(dateService.today()))
                .goalKeepers(getFantasyPlayers(playerRepository.getByPosition(PlayerPosition.GOAL_KEEPER, pageable)))
                .defenders(getFantasyPlayers(playerRepository.getByPosition(PlayerPosition.DEFENDER, pageable)))
                .middleFielders(getFantasyPlayers(playerRepository.getByPosition(PlayerPosition.MID_FIELDER, pageable)))
                .forwards(getFantasyPlayers(playerRepository.getByPosition(PlayerPosition.FORWARD, pageable)))
                .build();
    }

    public List<FantasyPlayerDTO> getFantasyPlayers(List<Player> players) {
        return players.stream().map(this::getFantasyPlayer).collect(Collectors.toList());
    }

    public FantasyPlayerDTO getFantasyPlayer(Player player) {
        var pageable = PageRequest.of(0, 1);
        var teamMatch = playerRepository.getTeamMatch(player, dateService.today(), pageable)
                .stream().findFirst().orElse(null);
        if (teamMatch == null) return null;
        var league = teamMatch.getMatch().getLeague();
        var team = teamMatch.getTeam();
        return FantasyPlayerDTO.builder()
                .leagueId(league.getLeagueId())
                .leagueName(league.getName())
                .name(player.getName())
                .playerId(player.getPlayerId())
                .score(player.getScoreAverage())
                .teamId(team.getTeamId())
                .teamName(team.getName())
                .build();
    }

}
