package com.esgi.stats19.api.matches.services;

import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.MatchBet;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.exceptions.InternalErrorException;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.MatchRepository;
import com.esgi.stats19.api.common.repositories.TeamMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamMatchRepository teamMatchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, TeamMatchRepository teamMatchRepository) {
        this.matchRepository = matchRepository;
        this.teamMatchRepository = teamMatchRepository;
    }

    public List<Match> getMatches() {
        return this.matchRepository.findAll();
    }

    public Match getMatch(Integer matchId) {
        return this.matchRepository.findById(matchId)
                .orElseThrow(() -> new NotFoundException("match " + matchId + " not exist"));
    }

    public Team getHomeTeam(Match match) {
        return match.getTeamMatches()
                .stream().filter(TeamMatch::isHome).findFirst()
                .orElseThrow(() -> new InternalErrorException("Internal Error"))
                .getTeam();
    }

    public Team getAwayTeam(@NotNull Match match) {
        return match.getTeamMatches()
                .stream().filter(team -> !team.isHome()).findFirst()
                .orElseThrow(() -> new InternalErrorException("Internal Error"))
                .getTeam();
    }

    public List<MatchBet> getBets(Integer matchId) {
        return getMatch(matchId).getBets();
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
