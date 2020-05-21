package com.esgi.stats19.api.leagues.services;

import com.esgi.stats19.api.common.entities.League;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.LeagueRepository;
import com.esgi.stats19.api.countries.services.CountryService;
import com.esgi.stats19.api.leagues.DTO.CreateLeagueDTO;
import com.esgi.stats19.api.leagues.DTO.UpdateLeagueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final CountryService countryService;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository, CountryService countryService) {
        this.leagueRepository = leagueRepository;
        this.countryService = countryService;
    }

    public List<League> getLeagues() {
        return this.leagueRepository.findAll();
    }

    public League getLeague(Integer leagueId) {
        return this.leagueRepository.findById(leagueId)
                .orElseThrow(() -> new NotFoundException("league " + leagueId + " not exist"));
    }

    public League createLeague(CreateLeagueDTO leagueDT0) {
        var country = this.countryService.getCountry(leagueDT0.getCountryId());

        var league = League.builder()
                .name(leagueDT0.getName())
                .country(country)
                .build();

        return this.leagueRepository.save(league);
    }

    public League updateLeague(UpdateLeagueDTO leagueDTO, Integer leagueId) {
        var league = this.getLeague(leagueId);

        league.setCountry(leagueDTO.getCountryId() != null ?
                this.countryService.getCountry(leagueDTO.getCountryId()) : league.getCountry());
        league.setName(leagueDTO.getName() != null ? leagueDTO.getName() : league.getName());

        return this.leagueRepository.save(league);
    }

    public void deleteLeague(Integer leagueId) {
        this.leagueRepository.deleteById(leagueId);
    }
}
