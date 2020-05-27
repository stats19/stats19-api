package com.esgi.stats19.api.bets.controllers.api;

import com.esgi.stats19.api.bets.DTO.GetBetWebsiteDTO;
import com.esgi.stats19.api.bets.services.BetDTOService;
import com.esgi.stats19.api.bets.services.BetWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bet_websites")
public class BetWebsiteAPIController {

    private final BetWebsiteService betWebsiteService;
    private final BetDTOService betDTOService;

    @Autowired
    public BetWebsiteAPIController(BetWebsiteService betWebsiteService, BetDTOService betDTOService) {
        this.betWebsiteService = betWebsiteService;
        this.betDTOService = betDTOService;
    }

    @GetMapping
    public List<GetBetWebsiteDTO> getBetWebsites() {
        return this.betDTOService.toResponse(this.betWebsiteService.getBetWebsites());
    }

    @GetMapping("/{betWebsiteId}")
    public GetBetWebsiteDTO getBetWebsite(@PathVariable Integer betWebsiteId) {
        return this.betDTOService.toResponse(this.betWebsiteService.getBetWebsite(betWebsiteId));
    }

}
