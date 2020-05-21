package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.MatchBet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchBetRepository extends JpaRepository<MatchBet, Integer> {
}
