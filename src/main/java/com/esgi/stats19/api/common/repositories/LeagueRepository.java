package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.League;
import com.esgi.stats19.api.common.entities.Match;
import com.esgi.stats19.api.common.entities.Team;
import com.esgi.stats19.api.common.entities.TeamMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LeagueRepository extends JpaRepository<League, Integer> {
    @Query(value = "SELECT tm FROM TeamMatch tm\n" +
            "JOIN Match sm on sm.matchApiId = tm.match.matchApiId\n" +
            "WHERE tm.match.matchApiId IN \n" +
            "\t(SELECT m.matchApiId FROM Match m WHERE m.league = ?1 AND m.season = ?2 AND m.played = true)\n" +
            "ORDER BY sm.date DESC")
    List<TeamMatch> getSeasonTeamMatches(League league, String season);

    @Query(value = "SELECT m FROM Match m WHERE m.league = ?1 AND m.season = ?2 AND m.played = true ORDER BY m.date DESC")
    List<Match> getSeasonMatches(League league, String season);

    @Query(value="SELECT m FROM Match m WHERE m.league = ?1 AND m.played = false AND m.date between ?2 AND ?3 ORDER BY m.date ASC")
    List<Match> getComingMatches(League league, Date start, Date end);

    @Query(value = "SELECT t \n" +
            "FROM Team t \n" +
            "WHERE t.teamApiId IN \n" +
            "  (SELECT tm.team.teamApiId FROM TeamMatch tm\n" +
            "    WHERE tm.match.matchApiId \n" +
            "    IN (SELECT m.matchApiId \n" +
            "        FROM Match m \n" +
            "        WHERE m.league = ?1 AND m.played = true AND m.season = ?2\n" +
            " )\n" +
            ")")
    List<Team> getSeasonTeams(League league, String season);
}
