package com.esgi.stats19.api.soccerSearch.services;

import com.esgi.stats19.api.common.repositories.PlayerRepository;
import com.esgi.stats19.api.common.repositories.TeamRepository;
import com.esgi.stats19.api.soccerSearch.DTO.SearchDTO;
import com.esgi.stats19.api.soccerSearch.DTO.SearchResultDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public SearchService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public SearchDTO getSearch(String searchStr) {
        return SearchDTO.builder()
                .players(getSearchPlayerResult(searchStr))
                .teams(getSearchTeamResult(searchStr))
                .build();
    }

    public List<SearchResultDTO> getSearchPlayerResult(String searchStr) {
        var pageable = PageRequest.of(0, 10);
        return this.playerRepository.getByNameIgnoreCaseContaining(searchStr, pageable).stream().map(player ->
                SearchResultDTO.builder().id(player.getPlayerId()).name(player.getName()).build()
        ).collect(Collectors.toList());
    }

    public List<SearchResultDTO> getSearchTeamResult(String searchStr) {
        var pageable = PageRequest.of(0, 10);
        return this.teamRepository.getByNameIgnoreCaseContaining(searchStr, pageable).stream().map(team ->
                SearchResultDTO.builder().id(team.getTeamId()).name(team.getName()).build()
        ).collect(Collectors.toList());
    }
}
