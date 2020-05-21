package com.esgi.stats19.api.countries.controllers;

import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.countries.DTO.GetCountryDTO;
import com.esgi.stats19.api.countries.services.CountryDTOService;
import com.esgi.stats19.api.countries.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/countries")
public class CountryAdminController {

    private final CountryService countryService;
    private final CountryDTOService countryDTOService;
    private final URIService uriService;

    @Autowired
    public CountryAdminController(CountryService countryService, CountryDTOService countryDTOService, URIService uriService) {
        this.countryService = countryService;
        this.countryDTOService = countryDTOService;
        this.uriService = uriService;
    }

    @PostMapping
    public ResponseEntity<GetCountryDTO> createCountry(@Validated @RequestBody String country) {
        var createdCountry = this.countryDTOService.toResponse(this.countryService.createCountry(country));
        return ResponseEntity.created(this.uriService.getCountry(createdCountry.getCountryId()))
                .body(createdCountry);
    }

    @PutMapping("/{countryId}")
    public GetCountryDTO updateCountry(@Validated @RequestBody String name, @PathVariable Integer countryId) {
        return this.countryDTOService.toResponse(this.countryService.updateCountry(name, countryId));
    }

    @DeleteMapping("/{countryId}")
    public void deleteCountry(@PathVariable Integer countryId){
        this.countryService.deleteCountry(countryId);
    }
}
