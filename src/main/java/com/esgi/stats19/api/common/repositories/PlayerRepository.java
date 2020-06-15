package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer>{
    List<Player> getByNameIgnoreCaseContaining(String search, Pageable pageable);
}