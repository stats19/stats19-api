package com.esgi.stats19.api.countries.services;

import com.esgi.stats19.api.common.entities.Country;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.countries.DTO.GetCountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryDTOService {


    private final URIService uriService;

    @Autowired
    public CountryDTOService(URIService uriService){
        this.uriService = uriService;
    }

    public GetCountryDTO toResponse(@NotNull Country country) {
        return GetCountryDTO.builder()
                .countryId(country.getCountryId())
                .name(country.getName())
                .leagues("leagues URL")
                .matches("matches URL")
                .players("players URL")
                .build();
    }

    public List<GetCountryDTO> toResponse(@NotNull List<Country> countries) {
        return countries.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
