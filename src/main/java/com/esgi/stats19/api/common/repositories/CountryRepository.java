package com.esgi.stats19.api.common.repositories;

import com.esgi.stats19.api.common.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
