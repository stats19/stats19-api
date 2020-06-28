package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.Player;
import com.esgi.stats19.api.common.entities.TeamMatch;
import com.esgi.stats19.api.common.enums.PlayerPosition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer>{
    List<Player> getByNameIgnoreCaseContaining(String search, Pageable pageable);
    List<Player> getByPosition(PlayerPosition position, Pageable pageable);
    @Query(value="SELECT t\n" +
            "FROM TeamMatch t\n" +
            "JOIN Match m on m.matchApiId = t.match.matchApiId \n" +
            "WHERE t IN \n" +
            "\t(SELECT tmp.teamMatch FROM TeamMatchPlayer tmp where tmp.player = ?1)\n" +
            "AND m.date < ?2 ORDER BY m.date DESC")
    List<TeamMatch> getTeamMatch(Player player, Date date, Pageable pageable);
}