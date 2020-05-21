package com.esgi.stats19.api.common.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class URIService {

    public URI getLeague(Integer leagueId) {
        return this.getURI("leagues", leagueId);
    }

    public URI getCountry(Integer countryId) { return this.getURI("countries", countryId); }

    public URI getTeam(Integer teamId) { return this.getURI("teams", teamId); }

//    public URI getLeagueMatches(Integer id) {
//    }

    private URI getURI(String path, Integer id) {return this.createURI("{root}/{id}", path, id);}

    private URI createURI(String pattern, Object ...params) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(pattern)
                .buildAndExpand(params)
                .toUri();
    }

}
