package com.esgi.stats19.api.soccer.matches.services;

import com.esgi.stats19.api.common.entities.*;
import com.esgi.stats19.api.common.exceptions.InternalErrorException;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.soccer.matches.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchDTOService {

    private final URIService uriService;
    private final MatchService matchService;

    @Autowired
    public MatchDTOService(URIService uriService, MatchService matchService){
        this.uriService = uriService;
        this.matchService = matchService;
    }

    public GetMatchDTO toResponse(@NotNull Match match) {
        return GetMatchDTO.builder()
                .matchId(match.getMatchId())
                .matchFifaId(match.getMatchApiId())
                .CountryName(match.getCountry().getName())
                .leagueName(match.getLeague().getName())
                .season(match.getSeason())
                .stage(match.getStage())
                .date(match.getDate().toString())
                .played(match.isPlayed())
                .home(this.toResponse(this.matchService.getHomeTeam(match)))
                .away(this.toResponse(this.matchService.getAwayTeam(match)))
                .bets(this.uriService.getBetsByMatch(match.getMatchId()).toString())
                .build();
    }

    public List<GetMatchDTO> toResponse(@NotNull List<Match> matches) {
        return matches.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public GetTeamMatchDTO toResponse(@NotNull TeamMatch teamMatch) {
        return GetTeamMatchDTO.builder()
                .teamId(teamMatch.getTeam().getTeamId())
                .name(teamMatch.getTeam().getName())
                .goals(teamMatch.getGoals())
                .possession(teamMatch.getPossession())
                .home(teamMatch.isHome())
                .team(this.uriService.getTeam(teamMatch.getTeam().getTeamId()).toString())
                .players(this.uriService.getPlayerByMatch(teamMatch.getMatch().getMatchId()).toString())
                .build();
    }

    public GetMatchPlayersDTO toResponse(List<TeamMatchPlayer> home, List<TeamMatchPlayer> away) {
        return GetMatchPlayersDTO.builder()
                .home(home.stream().map(this::toResponse).collect(Collectors.toList()))
                .away(away.stream().map(this::toResponse).collect(Collectors.toList()))
                .build();
    }

    public GetTeamMatchPlayerDTO toResponse(TeamMatchPlayer teamMatchPlayer) {
        var player = teamMatchPlayer.getPlayer();
        var team = teamMatchPlayer.getTeamMatch().getTeam();
        return GetTeamMatchPlayerDTO.builder()
                .playerId(player.getPlayerId())
                .name(player.getName())
                .player(this.uriService.getPlayer(player.getPlayerId()).toString())
                .teamName(team.getName())
                .team(this.uriService.getTeam(team.getTeamId()).toString())
                .positionX(teamMatchPlayer.getPositionX())
                .positionY(teamMatchPlayer.getPositionY())
                .firstTeam(teamMatchPlayer.isFirstTeam())
                .shots(teamMatchPlayer.getScored().stream().map(this::toResponse).collect(Collectors.toList()))
                .assists(teamMatchPlayer.getAssisted().stream().map(this::toResponse).collect(Collectors.toList()))
                .corners(teamMatchPlayer.getCorners().stream().map(this::toResponse).collect(Collectors.toList()))
                .crosses(teamMatchPlayer.getCrosses().stream().map(this::toResponse).collect(Collectors.toList()))
                .fouls(teamMatchPlayer.getCulprits().stream().map(this::toResponse).collect(Collectors.toList()))
                .build();
    }

    public GetShotDTO toResponse(Shot shot) {
        var shooter = shot.getScorer().getPlayer();
        var assist = shot.getAssist().getPlayer();
        return GetShotDTO.builder()
                .shotId(shot.getShotId())
                .scorerName(shooter.getName())
                .shooter(this.uriService.getPlayer(shooter.getPlayerId()).toString())
                .assistName(assist.getName())
                .assist(this.uriService.getPlayer(assist.getPlayerId()).toString())
                .elapsed(shot.getElapsed())
                .elapsedPlus(shot.getElapsedPlus())
                .type(shot.getType())
                .goalType(shot.getGoalType())
                .scored(shot.isScored())
                .onTarget(shot.isOnTarget())
                .build();
    }

    public GetCornerDTO toResponse(Corner corner) {
        var player = corner.getTeamMatchPlayer().getPlayer();
        return GetCornerDTO.builder()
                .cornerId(corner.getCornerId())
                .playerName(player.getName())
                .player(this.uriService.getPlayer(player.getPlayerId()).toString())
                .elapsed(corner.getElapsed())
                .elapsedPlus(corner.getElapsedPlus())
                .type(corner.getType())
                .build();
    }

    public GetCrossDTO toResponse(Cross cross) {
        var player = cross.getTeamMatchPlayer().getPlayer();

        return GetCrossDTO.builder()
                .crossId(cross.getCrossId())
                .playerName(player.getName())
                .player(this.uriService.getPlayer(player.getPlayerId()).toString())
                .elapsed(cross.getElapsed())
                .elapsedPlus(cross.getElapsedPlus())
                .type(cross.getType())
                .build();

    }

    public GetFoulDTO toResponse(Foul foul) {
        var victim = foul.getVictim().getPlayer();
        var culprit = foul.getCulprit().getPlayer();

        return GetFoulDTO.builder()
                .foulId(foul.getFoulId())
                .culpritName(culprit.getName())
                .culprit(this.uriService.getPlayer(culprit.getPlayerId()).toString())
                .victimName(victim.getName())
                .victim(this.uriService.getPlayer(victim.getPlayerId()).toString())
                .elapsed(foul.getElapsed())
                .elapsedPlus(foul.getElapsedPlus())
                .type(foul.getType())
                .card(foul.getCard().name())
                .build();
    }

}
