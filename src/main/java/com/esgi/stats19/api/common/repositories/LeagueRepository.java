package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Integer> {
}
