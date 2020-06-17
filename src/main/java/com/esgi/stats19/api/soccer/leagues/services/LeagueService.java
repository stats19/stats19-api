package com.esgi.stats19.api.soccer.leagues.services;

import com.esgi.stats19.api.common.entities.League;
import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.exceptions.ServerErrorException;
import com.esgi.stats19.api.common.repositories.LeagueRepository;
import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.countries.services.CountryService;
import com.esgi.stats19.api.soccer.leagues.DTO.CreateLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.UpdateLeagueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final CountryService countryService;
    private final DateService dateService;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository, CountryService countryService, DateService dateService) {
        this.leagueRepository = leagueRepository;
        this.countryService = countryService;
        this.dateService = dateService;
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

    public List<Match> getMatches(Integer leagueId) {
        var today = this.dateService.today();
        var limit = this.dateService.endDate();
        return this.getLeague(leagueId).getMatches().stream().filter(match ->
                today.compareTo(match.getDate()) * match.getDate().compareTo(limit) >= 0
        ).collect(Collectors.toList());
    }

    public League updateLeague(UpdateLeagueDTO leagueDTO, Integer leagueId) {
        var league = this.getLeague(leagueId);

        league.setCountry(leagueDTO.getCountryId() != null ?
                this.countryService.getCountry(leagueDTO.getCountryId()) : league.getCountry());
        league.setName(leagueDTO.getName() != null ? leagueDTO.getName() : league.getName());

        return this.leagueRepository.save(league);
    }

//    public

    public void deleteLeague(Integer leagueId) {
        this.leagueRepository.deleteById(leagueId);
    }
}
