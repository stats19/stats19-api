package com.esgi.stats19.api.countries.services;

import com.esgi.stats19.api.common.entities.Country;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getCountries() {
        return this.countryRepository.findAll();
    }

    public Country getCountry(Integer countryId) {
        return this.countryRepository
                .findById(countryId)
                .orElseThrow(() -> new NotFoundException("Country not found"));
    }

    public Country createCountry(String name) {
        var country = Country.builder().name(name).build();
        return this.countryRepository.save(country);
    }

    public Country updateCountry(String name, Integer countryId) {
        var country = this.getCountry(countryId);
        country.setName(name);
        return this.countryRepository.save(country);
    }

    public void deleteCountry(Integer countryId) {
        this.countryRepository.deleteById(countryId);
    }
}
