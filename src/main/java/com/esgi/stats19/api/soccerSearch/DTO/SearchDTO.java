package com.esgi.stats19.api.soccerSearch.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private List<SearchResultDTO> teams;
    private List<SearchResultDTO> players;
}
