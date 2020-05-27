package com.esgi.stats19.api.bets.services;

import com.esgi.stats19.api.bets.DTO.CreateBetDTO;
import com.esgi.stats19.api.common.entities.MatchBet;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.MatchBetRepository;
import com.esgi.stats19.api.matches.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetService {

    private final MatchBetRepository matchBetRepository;
    private final BetWebsiteService betWebsiteService;
    private final MatchService matchService;

    @Autowired
    public BetService(MatchBetRepository matchBetRepository, BetWebsiteService betWebsiteService,
                      MatchService matchService) {
            this.matchBetRepository = matchBetRepository;
            this.betWebsiteService = betWebsiteService;
            this.matchService = matchService;
    }

    public MatchBet getBet(Integer betId) {
        return this.matchBetRepository.findById(betId)
                .orElseThrow(() -> new NotFoundException("bet not found"));
    }


    public MatchBet createBet(CreateBetDTO betDTO) {
        var bet = MatchBet.builder()
                .betWebsite(this.betWebsiteService.getBetWebsite(betDTO.getBetWebsiteId()))
                .match(this.matchService.getMatch(betDTO.getMatchId()))
                .home(betDTO.getHome())
                .draw(betDTO.getDraw())
                .away(betDTO.getAway())
                .build();

        return this.matchBetRepository.save(bet);
    }

    // TODO: getBetByMatch
}
