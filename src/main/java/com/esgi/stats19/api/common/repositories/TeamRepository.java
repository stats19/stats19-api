package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.MatchBet;
import com.esgi.stats19.api.common.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
