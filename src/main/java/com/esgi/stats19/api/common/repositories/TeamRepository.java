package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.MatchBet;
import com.esgi.stats19.api.common.entities.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    List<Team> getByNameIgnoreCaseContainingOrShortNameContaining(String search, String str, Pageable pageable);
}
