package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.TeamMatchPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMatchPlayerRepository extends JpaRepository<TeamMatchPlayer, Integer> {
}
