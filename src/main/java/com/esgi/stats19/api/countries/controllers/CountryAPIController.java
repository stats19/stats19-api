package com.esgi.stats19.api.countries.controllers;

import com.esgi.stats19.api.countries.DTO.GetCountryDTO;
import com.esgi.stats19.api.countries.services.CountryDTOService;
import com.esgi.stats19.api.countries.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryAPIController {

    private final CountryService countryService;
    private final CountryDTOService countryDTOService;

    @Autowired
    public CountryAPIController(CountryService countryService, CountryDTOService countryDTOService) {
        this.countryService = countryService;
        this.countryDTOService = countryDTOService;
    }

    @GetMapping
    public List<GetCountryDTO> getLeagues() {
        return this.countryDTOService.toResponse(this.countryService.getCountries());
    }

    @GetMapping("/{leagueId}")
    public GetCountryDTO getLeague(@PathVariable Integer leagueId) {
        return this.countryDTOService.toResponse(this.countryService.getCountry(leagueId));
    }

}
