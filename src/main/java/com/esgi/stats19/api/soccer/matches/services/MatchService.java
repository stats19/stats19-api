package com.esgi.stats19.api.soccer.matches.services;

import com.esgi.stats19.api.common.entities.*;
import com.esgi.stats19.api.common.enums.Card;
import com.esgi.stats19.api.common.enums.Winner;
import com.esgi.stats19.api.common.exceptions.BadRequestException;
import com.esgi.stats19.api.common.exceptions.InternalErrorException;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.exceptions.ServerErrorException;
import com.esgi.stats19.api.common.repositories.MatchRepository;
import com.esgi.stats19.api.common.repositories.TeamMatchPlayerRepository;
import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.soccer.matches.DTO.GetLeagueDTO;
import com.esgi.stats19.api.soccer.matches.DTO.GetMatchDetailsFormattedDTO;
import com.esgi.stats19.api.soccer.matches.DTO.GetMatchFormattedDTO;
import com.esgi.stats19.api.soccer.matches.DTO.GetTeamMatchFormatted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamMatchPlayerRepository teamMatchPlayerRepository;
    private final DateService dateService;

    @Autowired
    public MatchService(MatchRepository matchRepository, TeamMatchPlayerRepository teamMatchPlayerRepository, DateService dateService) {
        this.matchRepository = matchRepository;
        this.teamMatchPlayerRepository = teamMatchPlayerRepository;
        this.dateService = dateService;
    }

    public List<Match> getMatches() {
        return this.matchRepository.findAll();
    }

    public List<Match> getMatches(boolean played) {
        if (played) {
            return this.matchRepository.findAllByPlayedIsTrue();
        }

        var now = dateService.today();
        var limit = dateService.endDate();
        return this.matchRepository.findAllByDateBetweenAndPlayedIsFalse(now, limit);
    }

    public List<Match> updatePlayedMatches() {
        var matches = matchRepository.findAllByPlayedIsFalseAndDateBefore(dateService.today());
        return matches.stream().peek(match -> match.setPlayed(true)).collect(Collectors.toList());
    }

    public List<Match> getMatchToScore() {
        return this.matchRepository.findAllByPlayedIsTrueAndScoreCalculatedIsFalse();
    }

    public Match getMatch(Integer matchId) {
        return this.matchRepository.findById(matchId)
                .orElseThrow(() -> new NotFoundException("match " + matchId + " not exist"));
    }

    public TeamMatch getHomeTeam(Match match) {
        return match.getTeamMatches()
                .stream().filter(TeamMatch::isHome).findFirst()
                .orElseThrow(() -> new InternalErrorException("Internal Error"));
    }

    public TeamMatch getAwayTeam(@NotNull Match match) {
        return match.getTeamMatches()
                .stream().filter(team -> !team.isHome()).findFirst()
                .orElseThrow(() -> new InternalErrorException("Internal Error"));
    }

    public List<MatchBet> getBets(Integer matchId) {
        return getMatch(matchId).getBets();
    }

    public List<TeamMatchPlayer> getHomePlayers(Match match) {
        return this.getHomeTeam(match).getTeamsMatchesPlayers();
    }

    public List<TeamMatchPlayer> getAwayPlayers(Match match) {
        return this.getAwayTeam(match).getTeamsMatchesPlayers();
    }

    public GetMatchFormattedDTO getMatchFormattedDTO(Integer matchId) {
        var match = this.getMatch(matchId);
        var league = match.getLeague();
        return GetMatchFormattedDTO.builder()
                .matchId(match.getMatchId())
                .league(GetLeagueDTO.builder().leagueId(league.getLeagueId())
                        .leagueName(league.getName()).build())
                .awayTeam(getTeamMatchFormatted(getAwayTeam(match)))
                .homeTeam(getTeamMatchFormatted(getHomeTeam(match)))
                .details(getMatchDetailsFormattedDTO(match))
                .build();
    }

    public void updatePlayerScore(Integer matchId, Integer playerId, Double score) {
        var match = this.getMatch(matchId);
        var playerOrNull = getHomePlayers(match).stream()
                .filter(p -> p.getPlayer() != null && p.getPlayer().getPlayerId().equals(playerId)).collect(Collectors.toList());
        if (playerOrNull.size() == 0) {
            playerOrNull = getAwayPlayers(match).stream()
                    .filter(p -> p.getPlayer() != null && p.getPlayer().getPlayerId().equals(playerId)).collect(Collectors.toList());
        }

        if (playerOrNull.size() != 1) {
            throw new ServerErrorException("too much player matches");
        }

        var p = playerOrNull.stream().findFirst().orElseThrow(() -> new BadRequestException("player not exist"));
        p.setScore(score);
        match.setScoreCalculated(true);
        this.matchRepository.save(match);
        this.teamMatchPlayerRepository.save(p);

    }

    public void forecastMatch(Integer matchId, Winner winner) {
        var match = getMatch(matchId);
        match.setForecast(winner);
        this.matchRepository.save(match);
    }

    public GetTeamMatchFormatted getTeamMatchFormatted(TeamMatch teamMatch) {
        var team = teamMatch.getTeam();
        return GetTeamMatchFormatted.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .possession(teamMatch.getPossession())
                .goals(teamMatch.getGoals())
                .shotOnTarget(teamMatch.getTeamsMatchesPlayers().stream().reduce(0, (shotsOnTarget, teamMatchPlayer2)
                        -> Math.toIntExact(shotsOnTarget + teamMatchPlayer2.getScored().stream().filter(Shot::isOnTarget).count()), Integer::sum))
                .fouls(teamMatch.getTeamsMatchesPlayers().stream().reduce(0, (fouls, teamMatchPlayer2)
                        -> fouls + teamMatchPlayer2.getCulprits().size(), Integer::sum))
                .yellowCards(countCards(Card.YELLOW_CARD, teamMatch))
                .redCards(countCards(Card.RED_CARD, teamMatch))
                .build();

    }

    public Integer countCards(Card cardType, TeamMatch teamMatch) {
        return teamMatch.getTeamsMatchesPlayers().stream().reduce(0, (yellowCard, teamMatchPlayer2)
                -> Math.toIntExact(yellowCard + teamMatchPlayer2.getCulprits()
                .stream().filter(card -> card.getCard() == cardType).count()), Integer::sum);
    }

    public List<GetMatchDetailsFormattedDTO> getMatchDetailsFormattedDTO(Match match) {
        List<GetMatchDetailsFormattedDTO> details = new ArrayList<>();

        match.getTeamMatches().forEach(
                teamMatch -> teamMatch.getTeamsMatchesPlayers().forEach(
                        teamMatchPlayer -> {
                            var player = teamMatchPlayer.getPlayer();
                            teamMatchPlayer.getCulprits().stream().filter(foul -> foul.getCard() != Card.NO_CARD).forEach(
                                    foul -> details.add(
                                            GetMatchDetailsFormattedDTO
                                                    .builder()
                                                    .playerId(player != null ? player.getPlayerId() : null)
                                                    .playerName(player != null ? player.getName() : null)
                                                    .elapsed(foul.getElapsed())
                                                    .elapsedPlus(foul.getElapsedPlus())
                                                    .home(teamMatch.isHome())
                                                    .type(foul.getCard() == Card.YELLOW_CARD ? 0 : foul.getCard() == Card.RED_CARD ? 1 : 2)
                                                    .build()
                                    )
                            );

                            teamMatchPlayer.getScored().stream().filter(Shot::isScored).forEach(
                                    shot -> details.add(
                                            GetMatchDetailsFormattedDTO
                                                    .builder()
                                                    .playerId(player != null ? player.getPlayerId() : null)
                                                    .playerName(player != null ? player.getName() : null)
                                                    .elapsed(shot.getElapsed())
                                                    .elapsedPlus(shot.getElapsedPlus())
                                                    .home(teamMatch.isHome())
                                                    .type(3)
                                                    .build()
                                    ));
                        }
                )
        );

        return details.stream()
                .sorted((d, d2) -> getElapsed(d2) - getElapsed(d)).collect(Collectors.toList());
    }

    public Integer getElapsed(GetMatchDetailsFormattedDTO getMatchDetailsFormattedDTO) {
        var elapsedPlus = getMatchDetailsFormattedDTO.getElapsedPlus() != null ? getMatchDetailsFormattedDTO.getElapsedPlus() : 0;
        return getMatchDetailsFormattedDTO.getElapsed() + elapsedPlus;
    }
}
