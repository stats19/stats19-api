package com.esgi.stats19.api.bets.services;

import com.esgi.stats19.api.bets.DTO.BetDTO;
import com.esgi.stats19.api.bets.DTO.GetBetDTO;
import com.esgi.stats19.api.bets.DTO.GetBetWebsiteDTO;
import com.esgi.stats19.api.common.entities.BetWebsite;
import com.esgi.stats19.api.common.entities.MatchBet;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.matches.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BetDTOService {


    private final URIService uriService;
    private final MatchService matchService;

    @Autowired
    public BetDTOService(URIService uriService, MatchService matchService){
        this.uriService = uriService;
        this.matchService = matchService;
    }

    public GetBetWebsiteDTO toResponse(@NotNull BetWebsite betWebsite) {
        return GetBetWebsiteDTO.builder()
                .name(betWebsite.getName())
                .url(betWebsite.getUrl())
                .bets(this.uriService.getBetsByBetWebsite(betWebsite.getBetWebsiteId()).toString())
                .build();
    }

    public List<GetBetWebsiteDTO> toResponse(@NotNull List<BetWebsite> betWebsites) {
        return betWebsites.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public GetBetDTO toResponse(@NotNull MatchBet matchBet) {
        return GetBetDTO.builder()
                .matchId(1)
                .betId(matchBet.getMatchBetId())
                .home(toResponse(this.matchService.getHomeTeam(matchBet.getMatch()), matchBet.getHome()))
                .away(toResponse(this.matchService.getAwayTeam(matchBet.getMatch()), matchBet.getAway()))
                .draw(matchBet.getDraw())
                .match(this.uriService.getMatch(matchBet.getMatch().getMatchId()).toString())
                .build();
    }

    public List<GetBetDTO> betToResponse(@NotNull List<MatchBet> matchBets) {
        return matchBets.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public BetDTO toResponse(Team team, Double odds) {
        return BetDTO.builder()
                .teamId(team.getTeamId())
                .teamName(team.getName())
                .odds(odds)
                .team(this.uriService.getTeam(team.getTeamId()).toString())
                .build();
    }

}
