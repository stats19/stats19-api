package com.esgi.stats19.api.soccer.leagues.services;

import com.esgi.stats19.api.common.entities.League;
import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.LeagueRepository;
import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.countries.services.CountryService;
import com.esgi.stats19.api.soccer.leagues.DTO.CreateLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.GetRankingDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.RankingItem;
import com.esgi.stats19.api.soccer.leagues.DTO.UpdateLeagueDTO;
import com.esgi.stats19.api.soccer.teams.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final CountryService countryService;
    private final DateService dateService;
    private final TeamService teamService;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository, CountryService countryService, DateService dateService, TeamService teamService) {
        this.leagueRepository = leagueRepository;
        this.countryService = countryService;
        this.dateService = dateService;
        this.teamService = teamService;
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

    public List<TeamMatch> getSeasonMatches(League league, String season) {
        return leagueRepository.getSeasonTeamMatches(league, season);
    }

    public List<Match> getMatches(League league, boolean played) {
        var today = this.dateService.today();
        var limit = this.dateService.endDate();

        if (played) return leagueRepository.getSeasonMatches(league, dateService.getSeason());
        return leagueRepository.getComingMatches(league, today, limit);
//        return league.getMatches().stream().filter(match -> {
//            if (played) return match.getSeason().equals(dateService.getSeason()) && match.isPlayed();
//            return match.getDate().compareTo(today) >= 0 && match.getDate().compareTo(limit) <= 0;
//        }).sorted((match, match2) -> {
//            if (played) return match2.getDate().compareTo(match.getDate());
//            return match.getDate().compareTo(match2.getDate());
//        }).collect(Collectors.toList());
    }

    public League updateLeague(UpdateLeagueDTO leagueDTO, Integer leagueId) {
        var league = this.getLeague(leagueId);

        league.setCountry(leagueDTO.getCountryId() != null ?
                this.countryService.getCountry(leagueDTO.getCountryId()) : league.getCountry());
        league.setName(leagueDTO.getName() != null ? leagueDTO.getName() : league.getName());

        return this.leagueRepository.save(league);
    }

    public GetRankingDTO getRankingByLeague(Integer leagueId, String season) {
        var league = getLeague(leagueId);
        var matches = getSeasonMatches(league, season);
        season = season != null ? season : dateService.getSeason();
        return GetRankingDTO.builder()
                .leagueId(league.getLeagueId())
                .leagueName(league.getName())
                .season(season)
                .rankingItems(getSeasonTeam(league, season)
                        .stream().map(team -> getRankingItem(team, matches))
                        .sorted((rankingItem, rankingItem2) -> {
                            var compare = rankingItem2.getPoints() - rankingItem.getPoints();
                            if (compare == 0) return rankingItem2.getScore() - rankingItem.getScore();
                            return compare;
                        })
                        .collect(Collectors.toList()))
                .build();
    }

    public RankingItem getRankingItem(Team team, List<TeamMatch> LeagueMatches) {
        var seasonMatches = teamService.getSeasonMatches(team, LeagueMatches);
        var matches = teamService.countMatchResult(team, seasonMatches);
        return RankingItem.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .matchPlayed(seasonMatches.size())
                .win(matches.getWins().size())
                .draw(matches.getDraw().size())
                .lose(matches.getLose().size())
                .score(teamService.countDiffScore(seasonMatches, team.getTeamId()))
                .points(matches.getWins().size() * 3 + matches.getDraw().size())
                .build();
    }

    public List<Team> getSeasonTeam(League league, String season) {
        return leagueRepository.getSeasonTeams(league, season);
    }


    public void deleteLeague(Integer leagueId) {
        this.leagueRepository.deleteById(leagueId);
    }
}
