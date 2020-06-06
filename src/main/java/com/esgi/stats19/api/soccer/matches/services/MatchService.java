package com.esgi.stats19.api.soccer.matches.services;

import com.esgi.stats19.api.common.entities.*;
import com.esgi.stats19.api.common.exceptions.InternalErrorException;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getMatches() {
        return this.matchRepository.findAll();
    }

    public List<Match> getMatches(boolean played) {
        if (played) {
            return this.matchRepository.findAllByPlayedIsTrue();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date now = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        return this.matchRepository.findAllByDateBetweenAndPlayedIsFalse(now, calendar.getTime());
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

    public List<TeamMatch> getTeams(Match match) {
        return match.getTeamMatches();
    }

//    public Match createMatch(CreateMatchDTO matchDT0) {
//        var match = Match.builder()
//                .name(matchDT0.getName())
//                .build();
//
//        return this.matchRepository.save(match);
//    }

    public void deleteMatch(Integer matchId) {
        this.matchRepository.deleteById(matchId);
    }
}
