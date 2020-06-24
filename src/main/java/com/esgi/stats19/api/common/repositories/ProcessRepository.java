package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Integer> {
    Process findByProcessName(String name);
}
