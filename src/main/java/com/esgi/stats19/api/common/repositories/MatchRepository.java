package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Integer> {
}
