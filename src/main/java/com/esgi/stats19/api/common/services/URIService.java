package com.esgi.stats19.api.common.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class URIService {

    public URI getLeague(Integer leagueId) {
        return this.getURI("leagues", leagueId);
    }

    public URI getCountry(Integer countryId) {
        return this.getURI("countries", countryId);
    }

    public URI getTeam(Integer teamId) {
        return this.getURI("teams", teamId);
    }

    public URI getBetWebsite(Integer betWebsiteId) {
        return this.getURI("bet_websites", betWebsiteId);
    }

    public URI getBetsByBetWebsite(Integer betWebsiteId) {
        return this.getSubURI("bet_website", "bets", betWebsiteId);
    }

    public URI getMatch(Integer matchId) {
        return this.getURI("matches", matchId);
    }

    public URI getPlayer(Integer playerId) {
        return this.getURI("players", playerId);
    }

    public URI getMatchesByTeam(Integer teamId) {
        return this.getSubURI("teams", "matches", teamId);
    }

    public URI getMatchByTeam(Integer teamId, Integer matchId) {
        return this.createURI("{root}/{teamId}/{sub}/{teamId}", "teams", teamId, "matches", matchId);
    }

    public URI getPlayerByMatch(Integer matchId) {
        return this.getSubURI("matches", "players", matchId);
    }

    public URI getBetsByMatch(Integer matchId) {
        return this.getSubURI("matches", "bets", matchId);
    }

//    public URI getLeagueMatches(Integer id) {
//    }

    private URI getURI(String path, Integer id) {
        return this.createURI("{root}/{id}", path, id);
    }

    private URI getSubURI(String root, String sub, Integer id) {
        return this.createURI("{root}/{id}/{sub}", root, id, sub);
    }

    private URI createURI(String pattern, Object... params) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(pattern)
                .buildAndExpand(params)
                .toUri();
    }

}
