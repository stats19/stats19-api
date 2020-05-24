package com.esgi.stats19.api.bets.services;

import com.esgi.stats19.api.bets.DTO.CreateBetWebsiteDTO;
import com.esgi.stats19.api.common.entities.BetWebsite;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.BetWebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetWebsiteService {

    private final BetWebsiteRepository betWebsiteRepository;

    @Autowired
    public BetWebsiteService(BetWebsiteRepository betWebsiteRepository) {
        this.betWebsiteRepository = betWebsiteRepository;
    }

    public List<BetWebsite> getBetWebsites() {
        return this.betWebsiteRepository.findAll();
    }

    public BetWebsite getBetWebsite(Integer betWebsiteId) {
        return this.betWebsiteRepository
                .findById(betWebsiteId)
                .orElseThrow(() -> new NotFoundException("BetWebsite not found"));
    }

    public BetWebsite createBet(CreateBetWebsiteDTO betWebsiteDTO) {
        var betWebsite = BetWebsite.builder()
                .name(betWebsiteDTO.getWebsiteName())
                .url(betWebsiteDTO.getWebsiteUrl())
                .build();
        return this.betWebsiteRepository.save(betWebsite);
    }

    public void deleteBet(Integer betId) {
        this.betWebsiteRepository.deleteById(betId);
    }
}
