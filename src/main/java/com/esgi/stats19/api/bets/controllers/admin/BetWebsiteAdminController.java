package com.esgi.stats19.api.bets.controllers.admin;

import com.esgi.stats19.api.bets.DTO.CreateBetWebsiteDTO;
import com.esgi.stats19.api.bets.DTO.GetBetDTO;
import com.esgi.stats19.api.bets.DTO.GetBetWebsiteDTO;
import com.esgi.stats19.api.bets.services.BetDTOService;
import com.esgi.stats19.api.bets.services.BetWebsiteService;
import com.esgi.stats19.api.common.services.URIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/bet_websites")
public class BetWebsiteAdminController {

    private final BetWebsiteService betWebsiteService;
    private final BetDTOService betDTOService;
    private final URIService uriService;

    @Autowired
    public BetWebsiteAdminController(BetWebsiteService betWebsiteService, BetDTOService betDTOService, URIService uriService) {
        this.betWebsiteService = betWebsiteService;
        this.betDTOService = betDTOService;
        this.uriService = uriService;
    }

    @PostMapping
    public ResponseEntity<GetBetWebsiteDTO> createBetWebsite(@Validated @RequestBody CreateBetWebsiteDTO betWebsiteDTO) {
        var createdBetWebsite = this.betDTOService.toResponse(this.betWebsiteService.createBet(betWebsiteDTO));
        return ResponseEntity.created(this.uriService.getBetWebsite(createdBetWebsite.getBetWebsiteId()))
                .body(createdBetWebsite);
    }

    @DeleteMapping("/{betWebsiteId}")
    public void deleteBet(@PathVariable Integer betWebsiteId){
        this.betWebsiteService.deleteBet(betWebsiteId);
    }
}
