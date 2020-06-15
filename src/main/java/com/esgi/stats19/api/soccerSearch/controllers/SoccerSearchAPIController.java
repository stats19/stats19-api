package com.esgi.stats19.api.soccerSearch.controllers;

import com.esgi.stats19.api.soccerSearch.DTO.SearchDTO;
import com.esgi.stats19.api.soccerSearch.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/search")
public class SoccerSearchAPIController {
    private final SearchService searchService;

    @Autowired
    public SoccerSearchAPIController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public SearchDTO getSearch(@NotNull @RequestParam String searchStr) {
        return this.searchService.getSearch(searchStr);
    }
}
