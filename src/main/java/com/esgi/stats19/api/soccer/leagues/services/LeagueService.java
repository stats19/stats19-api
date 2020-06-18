package com.esgi.stats19.api.soccer.leagues.services;

import com.esgi.stats19.api.common.entities.League;
import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.exceptions.NotFoundException;
import com.esgi.stats19.api.common.repositories.LeagueRepository;
import com.esgi.stats19.api.common.services.DateService;
import com.esgi.stats19.api.countries.services.CountryService;
import com.esgi.stats19.api.soccer.leagues.DTO.CreateLeagueDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.GetRankingDTO;
import com.esgi.stats19.api.soccer.leagues.DTO.RankingItem;
import com.esgi.stats19.api.soccer.leagues.DTO.UpdateLeagueDTO;
import com.esgi.stats19.api.soccer.matches.services.MatchService;
import com.esgi.stats19.api.soccer.teams.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final MatchService matchService;
    private final CountryService countryService;
    private final DateService dateService;
    private final TeamService teamService;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository, MatchService matchService, CountryService countryService, DateService dateService, TeamService teamService) {
        this.leagueRepository = leagueRepository;
        this.matchService = matchService;
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

    public GetRankingDTO getRankingByLeague(Integer leagueId) {
        var league = getLeague(leagueId);
        return GetRankingDTO.builder()
                .leagueId(league.getLeagueId())
                .leagueName(league.getName())
                .season(this.dateService.getSeason())
                .rankingItems(getSeasonTeam(league.getLeagueId())
                        .stream().map(this::gtoRankingItem)
                        .sorted(Comparator.comparingInt(RankingItem::getPoints).reversed())
                        .collect(Collectors.toList()))
                .build();
    }

    public RankingItem gtoRankingItem(Team team) {
        var matches = teamService.countMatchResult(team);
        return RankingItem.builder()
                .teamId(team.getTeamId())
                .name(team.getName())
                .matchPlayed(teamService.getSeasonMatches(team).size())
                .win(matches.getWins().size())
                .draw(matches.getDraw().size())
                .lose(matches.getLose().size())
                .score(teamService.countDiffScore(team))
                .points(matches.getWins().size() * 3 + matches.getDraw().size())
                .build();
    }

    public List<Team> getSeasonTeam(Integer leagueId) {
        HashSet<Integer> teams = new HashSet<>();
        getLeague(leagueId).getMatches().stream()
                .filter(match -> match.getSeason().equals(dateService.getSeason()) && match.isPlayed())
                .forEach(match -> {
                    match.getTeamMatches().forEach(teamMatch -> {
                        teams.add(teamMatch.getTeam().getTeamId());
                    });
                });

        return teams.stream().map(teamService::getTeam).collect(Collectors.toList());
    }


    public void deleteLeague(Integer leagueId) {
        this.leagueRepository.deleteById(leagueId);
    }
}
