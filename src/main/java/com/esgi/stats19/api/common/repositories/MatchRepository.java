package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findAllByPlayedIsTrue();
    List<Match> findAllByDateBetweenAndPlayedIsFalse(@NotNull Date start, @NotNull Date end);
}
