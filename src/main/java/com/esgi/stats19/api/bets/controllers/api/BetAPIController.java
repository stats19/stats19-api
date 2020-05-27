package com.esgi.stats19.api.bets.controllers.api;

import com.esgi.stats19.api.bets.DTO.GetBetDTO;
import com.esgi.stats19.api.bets.services.BetDTOService;
import com.esgi.stats19.api.bets.services.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bets")
public class BetAPIController {

    private final BetService betService;
    private final BetDTOService betDTOService;

    @Autowired
    public BetAPIController(BetService betService, BetDTOService betDTOService) {
        this.betDTOService = betDTOService;
        this.betService = betService;
    }

    @GetMapping("/{betId}")
    public GetBetDTO getBet(@PathVariable Integer betId) {
        return this.betDTOService.toResponse(this.betService.getBet(betId));
    }
}
