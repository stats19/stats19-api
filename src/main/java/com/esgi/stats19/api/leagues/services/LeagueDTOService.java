package com.esgi.stats19.api.leagues.services;

import com.esgi.stats19.api.common.entities.League;
import com.esgi.stats19.api.common.services.URIService;
import com.esgi.stats19.api.leagues.DTO.GetLeagueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueDTOService {

    private final URIService uriService;

    @Autowired
    public LeagueDTOService(URIService uriService){
        this.uriService = uriService;
    }

    public GetLeagueDTO toResponse(@NotNull League league) {
        return GetLeagueDTO.builder()
                .leagueId(league.getLeagueId())
                .name(league.getName())
                .country(league.getCountry().getName())
                .matches(this.uriService.getLeague(league.getLeagueId()).toString())
                .build();
    }

    public List<GetLeagueDTO> toResponse(@NotNull List<League> leagues) {
        return leagues.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
