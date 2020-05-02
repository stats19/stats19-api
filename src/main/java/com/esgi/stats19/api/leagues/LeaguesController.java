package com.esgi.stats19.api.leagues;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leagues")
public class LeaguesController {

    @GetMapping
    public GetLeaguesDTO getWorkspaces() {
        return GetLeaguesDTO.builder().name("Premier League").build();
    }
}
