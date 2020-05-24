package com.esgi.stats19.api.bets.controllers.process;

import com.esgi.stats19.api.bets.DTO.CreateBetDTO;
import com.esgi.stats19.api.bets.DTO.GetBetDTO;
import com.esgi.stats19.api.bets.services.BetDTOService;
import com.esgi.stats19.api.bets.services.BetService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process/bets")
public class BetProcessController {

    private final BetService betService;
    private final BetDTOService betDTOService;

    public BetProcessController(BetService betService, BetDTOService betDTOService) {
        this.betService = betService;
        this.betDTOService = betDTOService;
    }

    @PostMapping
    public GetBetDTO createBet(CreateBetDTO betDTO) {
        return this.betDTOService.toResponse(this.betService.createBet(betDTO));
    }
}
