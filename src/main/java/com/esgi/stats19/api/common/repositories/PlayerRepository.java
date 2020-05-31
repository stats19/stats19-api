package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer>{
}